package lk.ijse.thogakadeHibernateApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.thogakadeHibernateApp.entity.Item;
import lk.ijse.thogakadeHibernateApp.repository.ItemRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageItemsFormController {
    public Button btnsave;
    public Button btnupdate;
    public Button btndelete;
    @FXML
    private ImageView imgHome;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtunitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    void btnItemSave_OnAction(ActionEvent event) {
        Item item = getItem();

        ItemRepository itemRepository = new ItemRepository();
        boolean savedItem = itemRepository.saveItem(item);

        if (savedItem) {
            new Alert(Alert.AlertType.INFORMATION, "Item has been saved successfully").show();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item could not be saved.").show();
        }
    }

    @FXML
    void btnItemUpdate_OnAction(ActionEvent event) {
        int itemCode = Integer.parseInt(txtItemCode.getText());

        // Get the existing item from the database
        ItemRepository itemRepository = new ItemRepository();
        Item existingItem = itemRepository.getItem(itemCode);

        if (existingItem != null) {
            // Update the item properties
            existingItem.setDescription(txtItemDescription.getText());
            existingItem.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
            existingItem.setUnitPrice(Double.parseDouble(txtunitPrice.getText()));

            // Perform the update operation
            boolean isUpdated = itemRepository.updateItem(existingItem);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item has been Updated successfully").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item could not be updated.").show();
            }
        } else {
            // Show warning alert: Item not found
        }
    }

    @FXML
    void btnItemDelete_OnAction(ActionEvent event) {
        int itemCode = Integer.parseInt(txtItemCode.getText());

        // Get the existing item from the database
        ItemRepository itemRepository = new ItemRepository();
        Item existingItem = itemRepository.getItem(itemCode);

        if (existingItem != null) {
            // Prompt the user for confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this item?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Perform the delete operation
                boolean isDeleted = itemRepository.deleteItem(existingItem);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Item has been deleted successfully").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item could not be deleted.").show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Item not found").show();
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    private Item getItem() {
        Item item = new Item();
        item.setDescription(txtItemDescription.getText());
        item.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
        item.setUnitPrice(Double.parseDouble(txtunitPrice.getText()));
        return item;
    }

    @FXML
    private void clearFields() {
        txtItemCode.clear();
        txtItemDescription.clear();
        txtQtyOnHand.clear();
        txtunitPrice.clear();
    }



}
