package controller.dashboardController;

import db.DBConection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Item;
import model.dto.Order;
import model.dto.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashboardManagementController implements DashboardManagementInterface {

    ObservableList<String> custIdList = FXCollections.observableArrayList();
    ObservableList<String> itemCodeList = FXCollections.observableArrayList();
    String[] customer = new String[2];

    @Override
    public ObservableList<String> getAllCustomerId() {
        try {
            ResultSet resultSet = DBConection.getInstance().getConnection().prepareStatement("SELECT CustID FROM customer;").executeQuery();

            if (! custIdList.isEmpty()) custIdList.clear();

            while (resultSet.next()){
                custIdList.add(resultSet.getString("CustID"));
            }

            return custIdList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] searchCustomer(String ID) {
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("SELECT CustTitle,CustName FROM customer WHERE CustID = ?;");
            preparedStatement.setObject(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                customer[0] = resultSet.getString("CustTitle");
                customer[1] = resultSet.getString("CustName");
            }
            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<String> getAllItemCode() {
        try {
            ResultSet resultSet = DBConection.getInstance().getConnection().prepareStatement("SELECT ItemCode FROM item;").executeQuery();

            if (!itemCodeList.isEmpty()) itemCodeList.clear();

            while (resultSet.next()){
                itemCodeList.add(resultSet.getString("ItemCode"));
            }
            return itemCodeList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String code) {
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("SELECT Description,PackSize,UnitPrice,QtyOnHand FROM item WHERE ItemCode = ?;");
            preparedStatement.setObject(1,code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Item(
                        code,
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastOrderId() {
        try {
            ResultSet resultSet = DBConection.getInstance().getConnection().prepareStatement(" SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1;").executeQuery();
            if (resultSet.next()) return resultSet.getString("OrderID");
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addOrder(String orderId, LocalDate today, String custId) {
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("INSERT INTO Orders VALUES (?, ?, ?);");
            preparedStatement.setObject(1,orderId);
            preparedStatement.setObject(2,today);
            preparedStatement.setObject(3,custId);

            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addOrderdetails(ArrayList<OrderDetail>orderDetailArrayList){
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("INSERT INTO orderdetail VALUES (?, ?, ?, ?);");
            for (OrderDetail orderDetail : orderDetailArrayList){

                preparedStatement.setObject(1,orderDetail.getOrderId());
                preparedStatement.setObject(2,orderDetail.getItemCode());
                preparedStatement.setObject(3,orderDetail.getOrderQty());
                preparedStatement.setObject(4,orderDetail.getDiscount());

                if(preparedStatement.executeUpdate()>0){
                    if(!updateitemQty(orderDetail.getItemCode(), orderDetail.getOrderQty())){
                        return false;
                    }
                }

            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean updateitemQty(String itemCode, int qty){
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("UPDATE item SET QtyOnHand = QtyOnHand-? WHERE ItemCode = ?;");
            preparedStatement.setObject(1,qty);
            preparedStatement.setObject(2,itemCode);

            if(preparedStatement.executeUpdate()>0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addOrder(Order order) {
        if(addOrder(order.getOrderId(), order.getToday(), order.getCustId())){
            if(addOrderdetails(order.getOrderDetailArrayList())){
                return true;
            }
        }
        return false;
    }
}
