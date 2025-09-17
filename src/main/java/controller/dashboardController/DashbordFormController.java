package controller.dashboardController;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Item;
import model.dto.Order;
import model.dto.OrderDetail;
import model.dto.OrderItemDetails;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Button btnRemove;

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
    private TableColumn<?, ?> colTotalDiscount;

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
    private TableView<OrderItemDetails> tblOrderDetails;

    @FXML
    private JFXTextField txtDescount;

    @FXML
    private JFXTextField txtQty;

    DashboardManagementInterface dashboardManagement = new DashboardManagementController();
    ObservableList<OrderItemDetails> orderItemDetailList = FXCollections.observableArrayList();
    double total;
    int discount;
    String orderId;

    @FXML
    void btnAddItemOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(comboItemCode.getValue()==null){
            JOptionPane.showMessageDialog(null, "Please select an item to prepair the order...");
            return;
        }
        if (checktxtQty()){
            try {
                String itemCode = comboItemCode.getValue();
                String description = lblDescription.getText();
                String packSize = lblPackSize.getText();
                double unitPrice = Double.parseDouble(lblUnitPrice.getText().substring(3));
                int discount = 0;
                if (txtDescount.getText() != null || !txtDescount.getText().isEmpty()){
                    discount = Integer.parseInt(txtDescount.getText());
                }
                int qty = Integer.parseInt(txtQty.getText());
                double totalDiscount = discount*qty;
                double netTotal = unitPrice*qty-totalDiscount;

                OrderItemDetails newOrderItemDetails = new OrderItemDetails(
                        itemCode,
                        description,
                        packSize,
                        unitPrice,
                        discount,
                        qty,
                        totalDiscount,
                        netTotal
                );

                orderItemDetailList = tblOrderDetails.getItems();
                int index = isAlreadyExsist(itemCode, orderItemDetailList);

                if (index<0){
                    orderItemDetailList.add(newOrderItemDetails);
                    tblOrderDetails.setItems(orderItemDetailList);
                    calculateTotal(netTotal);
                    calculateDiscount((int) totalDiscount);
                }else {

                }

                txtQty.setText(null);
                txtDescount.setText("00");

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please Input valid number...");
            }
        }
    }

    @FXML
    void btnCancleOrderOnAction(ActionEvent event) {
        clearData();
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
        ObservableList<OrderItemDetails> items = tblOrderDetails.getItems();
        if (items.isEmpty()){
            JOptionPane.showMessageDialog(null, "Please add Items to plase the order...");
            return;
        }

        Order order = createOrder(createOrderDetails());
        boolean b = dashboardManagement.addOrder(order);
        if(b){
            JOptionPane.showMessageDialog(null, "Order has been placed successfully.....");
            clearData();
        }else {
            JOptionPane.showMessageDialog(null, ".....");
        }

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        TableView.TableViewSelectionModel<OrderItemDetails> selectionModel = tblOrderDetails.getSelectionModel();
        OrderItemDetails selectedItem = selectionModel.getSelectedItem();
        if(selectedItem == null){
            JOptionPane.showMessageDialog(null, "Please select an item to remove...");
            return;
        }
        orderItemDetailList = tblOrderDetails.getItems();
        ObservableList<OrderItemDetails> orderItemDetailList1 = removeItem(selectedItem, orderItemDetailList);
        tblOrderDetails.setItems(orderItemDetailList1);
    }

    private ObservableList<OrderItemDetails> removeItem(OrderItemDetails selectedItem, ObservableList<OrderItemDetails> orderItemDetailList) {
        for (int i = 0; i < orderItemDetailList.size(); i++) {
            if (selectedItem.getItemCode().equals(orderItemDetailList.get(i).getItemCode())){
                orderItemDetailList.remove(i);
                if (orderItemDetailList.isEmpty()){
                    total=0;
                    discount=0;
                    setTotal(00);
                    setDiscount(00);
                }else {
                    calculateTotal(-selectedItem.getNetTotal());
                    calculateDiscount(-selectedItem.getDiscount());
                }
            }
        }
        return orderItemDetailList;
    }

    @FXML
    void comboCustIdOnAction(ActionEvent event) {
        if(comboCustId.getValue() != null)comboItemCode.setDisable(false);
        String[] customer = dashboardManagement.searchCustomer(comboCustId.getValue());
        lblName.setText(customer[0]+"."+customer[1]);
    }

    @FXML
    void comboItemCodeOnAction(ActionEvent event) {
        if (comboItemCode.getValue()==null){
            return;
        }
        Item item = dashboardManagement.searchItem(comboItemCode.getValue());
        lblDescription.setText(item.getDescription());
        lblPackSize.setText(item.getPackSize());
        lblUnitPrice.setText("Rs."+item.getUnitPrice());
        lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddOnAction(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(getData().toString());
        loadCustId();
        loadItemCode();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDescount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalDiscount.setCellValueFactory(new PropertyValueFactory<>("totalDiscount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));

        generateOrderId();

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


    private boolean checktxtQty(){
        if (txtQty.getText() == null || txtQty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Item QTY...");
            return false;
        }
        return true;
    }

    private int isAlreadyExsist(String itemCode, ObservableList<OrderItemDetails> list){
        for (int i = 0; i < list.size(); i++) {
            if (itemCode.equals(list.get(i).getItemCode())){
                return i;
            }
        }
        return -1;
    }

    private void calculateTotal(double netTotal){
        total+=netTotal;
        setTotal(total);
    }

    private void setTotal(double total) {
        lblTotal.setText(total+"0");
    }

    private void calculateDiscount(int totalDiscount){
        discount+=totalDiscount;
        setDiscount(discount);
    }

    private void setDiscount(int discount) {
        lblDescount.setText(discount+".00");
    }

    private void clearData(){
        comboCustId.setValue(null);
        lblName.setText(null);
        comboItemCode.setDisable(true);
        comboItemCode.setValue(null);
        lblDescription.setText(null);
        lblPackSize.setText(null);
        lblUnitPrice.setText(null);
        lblQtyOnHand.setText(null);
        txtQty.setText(null);
        txtDescount.setText("00");
        orderItemDetailList.clear();
        tblOrderDetails.setItems(orderItemDetailList);
        total=0.0;
        setTotal(total);
        discount=0;
        setDiscount(discount);
    }

    private String generateOrderId(){
        String newid=String.format("D%03d",Integer.parseInt(dashboardManagement.getLastOrderId().substring(1))+1);
        orderId = newid;
        return newid;
    }

    private Order createOrder(ArrayList<OrderDetail> orderDetailArrayList){

        return new Order(
                orderId,
                getData(),
                comboCustId.getValue(),
                orderDetailArrayList
        );
    }

    private ArrayList<OrderDetail> createOrderDetails(){
        ObservableList<OrderItemDetails> items = tblOrderDetails.getItems();
        ArrayList<OrderDetail>orderDetailArrayList = new ArrayList<>();
        for(OrderItemDetails ob:items){
            orderDetailArrayList.add(new OrderDetail(
                    orderId,
                    ob.getItemCode(),
                    ob.getQty(),
                    ob.getDiscount()
            ));
        }
        return orderDetailArrayList;
    }


}
