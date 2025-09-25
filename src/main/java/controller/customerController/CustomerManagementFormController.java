package controller.customerController;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Customer;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private JFXComboBox<String> comboCity;

    @FXML
    private JFXComboBox<String> comboProvince;

    @FXML
    private JFXComboBox<String> comboTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private Label lblId;

    @FXML
    private TableView<Customer> tblCustData;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtSalary;

    private CustomerManagementInterface customerManagement = new CustomerManagementController();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(checkInputField()){

            try{
                Customer customer = new Customer(
                        lblId.getText(),
                        comboTitle.getValue(),
                        txtName.getText(),
                        dateDob.getValue(),
                        Double.parseDouble(txtSalary.getText()),
                        txtAddress.getText(),
                        comboCity.getValue(),
                        comboProvince.getValue(),
                        txtPostalCode.getText()
                );

                if (customerManagement.addCustomer(customer)){
                    showMessage("Customer Successfully added to the ");
                    clear();
                    loadTable();
                    loadCustId();

                }else {
                    showMessage("ERROR...\nCustomer Adding Process Failed...\nPlease Try Again...");
                }

            }catch (NumberFormatException e){
                showMessage("ERROR... Please Input Valid Salary Amount...");
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
        loadTable();
        loadCustId();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(customerManagement.deleteCustomer(lblId.getText())){
            showMessage("Customer Successfully Deleted...");
            clear();
            loadTable();
            loadCustId();
        }else {
            showMessage("Customer Deleting Process Failed... Please Try Again...");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(checkInputField()){
            try {
                Customer customer = new Customer(
                        lblId.getText(),
                        comboTitle.getValue(),
                        txtName.getText(),
                        dateDob.getValue(),
                        Double.parseDouble(txtSalary.getText()),
                        txtAddress.getText(),
                        comboCity.getValue(),
                        comboProvince.getValue(),
                        txtPostalCode.getText()
                );

                if (customerManagement.updateCustomer(customer)){
                    showMessage("Customer Details Successfully Updated...");
                    clear();
                    loadTable();
                    loadCustId();
                }else {
                    showMessage("Customer Details Updating Process Failed... Please Try Again...");
                }

            }catch (NumberFormatException e){
                showMessage("ERROR... Please Input Valid Salary Amount...");
            }
        }
    }

    @FXML
    void comboProvinceOnAction(ActionEvent event) {

        if (comboCity.isDisabled()) {
            comboCity.setDisable(false);
        }
        if(comboProvince.getValue() == null){
            return;
        }
        if (comboProvince.getValue().equals("Western")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Colombo", "Gampaha", "Kaluthara");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Central")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Kandy", "Matale", "Nuwara Eliya");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Southern")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Galle", "Mathara", "Hanbantota");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Jaffna", "Kilinochchi", "Mannar", "Mullaitivu", "Vavuniya");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Eastern")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Trinkomalee", "Batticaloa", "Ampara");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North Western")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Kurunegala", "Puttalama");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("North Central")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Anuradhapura", "Polonnaruwa");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Uva")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Badulla", "Monaragala");
            comboCity.setItems(cityList);
            return;
        }
        if (comboProvince.getValue().equals("Sabaragamuwa")) {
            ObservableList<String> cityList = FXCollections.observableArrayList("Ratnapura", "Kegalle");
            comboCity.setItems(cityList);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        loadTitle();
        loadProvince();
        loadCustId();
        loadTable();

        tblCustData.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            if(newSelection != null){
                setCustData(newSelection);
            }
        });
    }

    private void loadTable(){
        tblCustData.setItems(getallCustomers());
    }

    private ObservableList<Customer> getallCustomers(){
        return customerManagement.getAllCustomers();
    }

    private void loadTitle(){
        ObservableList<String> titleList = FXCollections.observableArrayList("Mr.", "Ms.", "Mrs.", "Miss.");
        comboTitle.setItems(titleList);
    }

    private void loadProvince(){
        ObservableList<String> provinceList = FXCollections.observableArrayList(
                "Western",
                "Central",
                "Southern",
                "North",
                "Eastern",
                "North Western",
                "North Central",
                "Uva",
                "Sabaragamuwa"
        );
        comboProvince.setItems(provinceList);
    }

    private void loadCustId(){
        lblId.setText(customerManagement.getNewId());
    }

    private boolean checkInputField(){
        if (comboTitle.getValue() == null){
            showMessage("ERROR...Please Select Customer Title...");
            return false;
        }
        if(txtName.getText() == null || txtName.getText().isEmpty()){
            showMessage("ERROR...Please Input Customer Name...");
            return false;
        }
        if(dateDob.getValue() == null ){
            showMessage("ERROR...Please Input Customer Birthday...");
            return false;
        }
        if(txtSalary.getText() == null || txtSalary.getText().isEmpty()){
            showMessage("ERROR...Please Input Customer Salary...");
            return false;
        }
        if (comboProvince.getValue() == null){
            showMessage("ERROR...Please Select Customer Province...");
            return false;
        }
        if(comboCity.getValue() == null){
            showMessage("ERROR...Please Select Customer City...");
            return false;
        }
        if(txtAddress.getText() == null || txtAddress.getText().isEmpty()){
            showMessage("ERROR...Please Input Customer Address...");
            return false;
        }
        if(txtPostalCode.getText() == null || txtPostalCode.getText().isEmpty()){
            showMessage("ERROR...Please Input Customer Postal Code...");
            return false;
        }

        return true;
    }

    private void setCustData(Customer customer){
        lblId.setText(customer.getId());
        comboTitle.setValue(customer.getTitle());
        txtName.setText(customer.getName());
        dateDob.setValue(customer.getDob());
        txtSalary.setText(String.valueOf(customer.getSalary()));
        txtAddress.setText(customer.getAddress());
        comboProvince.setValue(customer.getProvince());
        comboCity.setValue(customer.getCity());
        txtPostalCode.setText(customer.getPostalCode());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void clear(){
        comboTitle.setValue(null);
        txtName.setText(null);
        dateDob.setValue(null);
        txtSalary.setText(null);
        comboProvince.setValue(null);
        comboCity.setDisable(true);
        comboCity.setValue(null);
        txtAddress.setText(null);
        txtPostalCode.setText(null);
    }
}