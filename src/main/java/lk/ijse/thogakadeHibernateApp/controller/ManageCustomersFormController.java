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
import java.util.ResourceBundle;


public class ManageCustomersFormController implements Initializable {
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
        int savedCusId = customerRepository.saveCustomer(customer);
        System.out.println("Saved Cus Id: " + savedCusId);
    }

    @FXML
    public void btnDelete_OnAction(ActionEvent actionEvent) {

    }

    @FXML
    public void btnUpdate_OnAction(ActionEvent actionEvent) {

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


}
