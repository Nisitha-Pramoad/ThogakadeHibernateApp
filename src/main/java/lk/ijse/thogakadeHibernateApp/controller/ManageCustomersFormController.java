package lk.ijse.thogakadeHibernateApp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.thogakadeHibernateApp.embedded.Address;
import lk.ijse.thogakadeHibernateApp.embedded.MobileNo;
import lk.ijse.thogakadeHibernateApp.embedded.NameIdentifier;
import lk.ijse.thogakadeHibernateApp.entity.Customer;
import lk.ijse.thogakadeHibernateApp.repository.CustomerRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class ManageCustomersFormController implements Initializable {
    @FXML
    public TextField txtCustomerId;
    @FXML
    public AnchorPane root;
    @FXML
    public TextField txtCustomerFirstName;
    @FXML
    public TextField txtCustomerMiddleName;
    @FXML
    public TextField txtCustomerLastName;
    @FXML
    public TextField txtCustomerAddressLine1;
    @FXML
    public TextField txtCustomerAddressLine2;
    @FXML
    public Button btnsave;
    @FXML
    public Button btnupdate;
    @FXML
    public Button btndelete;
    @FXML
    public TableView tblCustomers;
    @FXML
    public ImageView imgHome;
    @FXML
    public TextField txtCustomerMobile;
    @FXML
    public ComboBox mobileNumberType;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> mobileNoTypeList = FXCollections.observableArrayList(
                "HOME",
                "MOBILE"
        );
        mobileNumberType.setItems(mobileNoTypeList);
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

    @FXML
    public void btnSave_OnAction(ActionEvent actionEvent) {
        Customer customer = getCustomer();

        CustomerRepository customerRepository = new CustomerRepository();
        boolean savedCusId = customerRepository.saveCustomer(customer);
        System.out.println("Saved Cus Id: " + savedCusId);

        if (savedCusId) {
            new Alert(Alert.AlertType.INFORMATION, "Customer has been saved successfully").show();
            clearFields();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer has not been saved successfully").show();
        }
    }

    @FXML
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        int cusId = Integer.parseInt(txtCustomerId.getText());

        // Get the existing customer from the database
        CustomerRepository cusRepository = new CustomerRepository();
        Customer existingCustomer = cusRepository.getCustomer(cusId);

        if (existingCustomer != null) {
            // Prompt the user for confirmation
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Perform the delete operation
                boolean isDeleted = cusRepository.deleteCustomer(existingCustomer);
                if (isDeleted) {
                    System.out.println("Customer Deleted!");
                    new Alert(Alert.AlertType.INFORMATION, "Customer has been deleted successfully").show();
                    clearFields();
                } else {
                    System.out.println("Customer Deletion Failed!");
                    new Alert(Alert.AlertType.ERROR, "Customer deletion has failed").show();
                }
            }
        } else {
            System.out.println("Customer not found!");
            new Alert(Alert.AlertType.WARNING, "Customer not found").show();
        }
    }


    @FXML
    public void btnUpdate_OnAction(ActionEvent actionEvent) {
        int cusId = Integer.parseInt(txtCustomerId.getText());

        // Get the existing customer from the database
        CustomerRepository cusRepository = new CustomerRepository();
        Customer existingCustomer = cusRepository.getCustomer(cusId);

        if (existingCustomer != null) {
            // Update customer's name
            NameIdentifier updatedName = getNameIdentifier();
            existingCustomer.setNameIdentifier(updatedName);

            // Update customer's address
            Address updatedAddress = getAddress();
            existingCustomer.setCustomerAddress(updatedAddress);

            // Update customer's mobile number
            List<MobileNo> updatedMobileNos = getMobileNos();
            existingCustomer.setPhoneNos(updatedMobileNos);

            // Perform the update operation
            boolean isUpdated = cusRepository.updateCustomer(existingCustomer);
            if (isUpdated) {
                System.out.println("Customer Updated!");
                new Alert(Alert.AlertType.INFORMATION, "Customer has been updated successfully").show();
                clearFields();
            } else {
                System.out.println("Customer Update Failed!");
                new Alert(Alert.AlertType.ERROR, "Customer update has failed").show();
            }
        } else {
            System.out.println("Customer not found!");
            new Alert(Alert.AlertType.WARNING, "Customer not found").show();
        }
    }


    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1);

        NameIdentifier nameIdentifier = getNameIdentifier();
        customer.setNameIdentifier(nameIdentifier);

        Address cusaddress = getAddress();
        customer.setCustomerAddress(cusaddress);

        List<MobileNo> mobileNos = getMobileNos();
        customer.setPhoneNos(mobileNos);

        return customer;
    }

    private List<MobileNo> getMobileNos() {
        String cusMobileNo = txtCustomerMobile.getText();
        String cusMobileType = mobileNumberType.getValue().toString();

        List<MobileNo> mobileNos = new ArrayList<>();
        MobileNo mobileNo = new MobileNo();
        mobileNo.setType(cusMobileType);
        mobileNo.setMobileNo(cusMobileNo);
        mobileNos.add(mobileNo);

        return mobileNos;
    }

    private NameIdentifier getNameIdentifier() {
        String firstName = txtCustomerFirstName.getText();
        String middleName = txtCustomerMiddleName.getText();
        String lastName = txtCustomerLastName.getText();

        NameIdentifier nameIdentifier = new NameIdentifier();
        nameIdentifier.setFirstName(firstName);
        nameIdentifier.setMiddleName(middleName);
        nameIdentifier.setLastName(lastName);
        return nameIdentifier;
    }

    private Address getAddress() {
        String addressline1 = txtCustomerAddressLine1.getText();
        String addressline2 = txtCustomerAddressLine2.getText();

        Address address = new Address();
        address.setAddressLine1(addressline1);
        address.setAddressLine2(addressline2);
        return address;
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerFirstName.clear();
        txtCustomerMiddleName.clear();
        txtCustomerLastName.clear();
        txtCustomerAddressLine1.clear();
        txtCustomerAddressLine2.clear();
        txtCustomerMobile.clear();
        mobileNumberType.getSelectionModel().clearSelection();
    }
}
