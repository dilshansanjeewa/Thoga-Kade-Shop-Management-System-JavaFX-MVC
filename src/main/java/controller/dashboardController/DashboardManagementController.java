package controller.dashboardController;

import db.DBConection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardManagementController implements DashboardManagementInterface{

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
    public String[] getCustomer(String ID) {
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
}
