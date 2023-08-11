package lk.ijse.thogakadeHibernateApp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;


public class ManageCustomersFormController {
    public AnchorPane root;
    public static TextField txtCustomerFirstName;
    public static TextField txtCustomerMiddleName;
    public static TextField txtCustomerLastName;
    public static TextField txtCustomerAddressLine1;
    public static TextField txtCustomerAddressLine2;
    public Button btnsave;
    public Button btnupdate;
    public Button btndelete;
    public TableView tblCustomers;
    public ImageView imgHome;
    public static TextField txtCustomerMobile;
    public static ComboBox mobileNumberType;


    public void initialize() {
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


    public void btnSave_OnAction(ActionEvent actionEvent) {
        Customer customer = getCustomer();

        CustomerRepository customerRepository = new CustomerRepository();
        int savedCusId = customerRepository.saveCustomer(customer);
        System.out.println("Saved Cus Id: " + savedCusId);
    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {

    }


    public void btnUpdate_OnAction(ActionEvent actionEvent) {

    }

    private static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        NameIdentifier nameIdentifier = getNameIdentifier();
        customer.setNameIdentifier(nameIdentifier);

        List<MobileNo> mobileNos = getMobileNos();
        customer.setPhoneNos(mobileNos);
        return customer;
    }

    private static List<MobileNo> getMobileNos() {
        List<MobileNo> mobileNos = new ArrayList<>();
        MobileNo mobileNo = new MobileNo();
        mobileNo.setType(String.valueOf(mobileNumberType));
        mobileNo.setMobileNo(String.valueOf(txtCustomerMobile));
        mobileNos.add(mobileNo);

        return mobileNos;
    }

    private static NameIdentifier getNameIdentifier() {
        NameIdentifier nameIdentifier = new NameIdentifier();
        nameIdentifier.setFirstName(String.valueOf(txtCustomerFirstName));
        nameIdentifier.setMiddleName(String.valueOf(txtCustomerMiddleName));
        nameIdentifier.setLastName(String.valueOf(txtCustomerLastName));
        return nameIdentifier;
    }

    private static Address getAddress() {
        Address address = new Address();
        address.setAddressLine1(String.valueOf(txtCustomerAddressLine1));
        address.setAddressLine2(String.valueOf(txtCustomerAddressLine2));
        return address;
    }
}
