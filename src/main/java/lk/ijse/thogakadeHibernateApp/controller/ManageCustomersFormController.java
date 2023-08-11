package lk.ijse.thogakadeHibernateApp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;


public class ManageCustomersFormController {
    public AnchorPane root;
    public TextField txtcustomerId;
    public TextField txtCustomerFirstName;
    public TextField txtCustomerAddressLine1;
    public TextField txtCustomerMiddleName;
    public TextField txtCustomerLastName;
    public TextField txtCustomerAddressLine2;
    public Button btnsave;
    public Button btnupdate;
    public Button btndelete;
    public TableView tblCustomers;
    public ImageView imgHome;
    public TextField txtCustomerMobile;


    public void initialize() {

    }


    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }


    public void btnSave_OnAction(ActionEvent actionEvent) {

    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {

    }


    public void btnUpdate_OnAction(ActionEvent actionEvent) {

    }
}
