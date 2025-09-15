package controller.dashboardController;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashbordFormController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddItem;

    @FXML
    private Button btnCancleOrder;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnManageOrder;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnOrderHistory;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableColumn<?, ?> colDescount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comboCustId;

    @FXML
    private ComboBox<String> comboItemCode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescount;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPackSize;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<?> tblOrderDetails;

    @FXML
    private JFXTextField txtDescount;

    @FXML
    private JFXTextField txtQty;

    DashboardManagementInterface dashboardManagement = new DashboardManagementController();

    @FXML
    void btnAddItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancleOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderHistoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void comboCustIdOnAction(ActionEvent event) {
        String[] customer = dashboardManagement.getCustomer(comboCustId.getValue());
        lblName.setText(customer[0]+"."+customer[1]);
    }

    @FXML
    void comboItemCodeOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(getData().toString());
        loadCustId();
        loadItemCode();
    }

    private LocalDate getData(){

        return LocalDate.now();
    }

    private void loadCustId(){
        comboCustId.setItems(dashboardManagement.getAllCustomerId());
    }

    private void loadItemCode(){
        comboItemCode.setItems(dashboardManagement.getAllItemCode());
    }
}
