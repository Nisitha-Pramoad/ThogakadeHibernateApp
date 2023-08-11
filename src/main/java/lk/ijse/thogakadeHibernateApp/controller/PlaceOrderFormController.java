package lk.ijse.thogakadeHibernateApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.thogakadeHibernateApp.entity.Item;
import lk.ijse.thogakadeHibernateApp.repository.PlaceOrderRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PlaceOrderFormController {

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private ComboBox<Integer> cmbCustomerSelecter; // Assuming customer IDs are integers

    @FXML
    private ComboBox<String> cmbItemSelecter; // Assuming item descriptions are strings

    @FXML
    private ImageView imgHome;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTotal;

    @FXML
    private TextField txtICustomerName;

    @FXML
    private TextField txtItemDescription;

    @FXML
    private TextField txtQtyOnHandAvailable;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtunitPrice;

    @FXML
    private AnchorPane root;

    private PlaceOrderRepository placeOrderRepository;

    @FXML
    public void initialize() {
        placeOrderRepository = new PlaceOrderRepository();

        // Load customer IDs from the database and populate the ComboBox
        List<Integer> customerIds = placeOrderRepository.getAllCustomerIds();
        cmbCustomerSelecter.getItems().addAll(customerIds);

        // Load item descriptions from the database and populate the ComboBox
        List<String> itemIds = placeOrderRepository.getAllItemIds();
        cmbItemSelecter.getItems().addAll(itemIds);

        // Listener for customer selection
        cmbCustomerSelecter.setOnAction(event -> {
            Integer selectedCustomerId = cmbCustomerSelecter.getValue();
            if (selectedCustomerId != null) {
                String customerName = placeOrderRepository.getCustomerName(selectedCustomerId);
                txtICustomerName.setText(customerName);
            }
        });

        // Listener for item selection
        cmbItemSelecter.setOnAction(event -> {
            String selectedItemCode = cmbItemSelecter.getValue();
            if (selectedItemCode != null) {
                Item selectedItem = placeOrderRepository.getItemData(selectedItemCode);
                txtItemDescription.setText(selectedItem.getDescription());
                txtQtyOnHandAvailable.setText(Integer.toString(selectedItem.getQtyOnHand()));
                txtunitPrice.setText(Double.toString(selectedItem.getUnitPrice()));
            }
        });

    }

    @FXML
    void btnOrderSave_OnAction(ActionEvent event) {
        // Get selected customer ID and item description
        Integer selectedCustomerId = cmbCustomerSelecter.getValue();
        String selectedItemDescription = cmbItemSelecter.getValue();

        if (selectedCustomerId != null && selectedItemDescription != null) {
            // Get quantity and calculate total
            int quantity = Integer.parseInt(txtqty.getText());
            double unitPrice = Double.parseDouble(txtunitPrice.getText());
            double total = quantity * unitPrice;

            // Save order in the database
            boolean savedOrder = placeOrderRepository.saveOrder(selectedCustomerId, selectedItemDescription, quantity, total);

            if (savedOrder) {
                lblTotal.setText(Double.toString(total)); // Display the total
                clearFields(); // Clear input fields
            } else {
                // Show error alert
                new Alert(Alert.AlertType.ERROR, "Failed to save order.").show();
            }
        }
    }

    // Other methods and event handlers as before

    private void clearFields() {
        // Clear input fields
        cmbCustomerSelecter.getSelectionModel().clearSelection();
        cmbItemSelecter.getSelectionModel().clearSelection();
        txtICustomerName.clear();
        txtItemDescription.clear();
        txtQtyOnHandAvailable.clear();
        txtqty.clear();
        txtunitPrice.clear();
    }

    public void btnOrderUpdate_OnAction(ActionEvent actionEvent) {
    }

    public void btnOrderDelete_OnAction(ActionEvent actionEvent) {
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
