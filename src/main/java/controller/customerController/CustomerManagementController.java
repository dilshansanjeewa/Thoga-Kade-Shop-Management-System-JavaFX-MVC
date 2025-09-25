package controller.customerController;

import db.DBConection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManagementController implements CustomerManagementInterface{

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public String getNewId() {
        return generateId();
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {

            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getTitle());
            preparedStatement.setObject(3, customer.getName());
            preparedStatement.setObject(4, customer.getDob());
            preparedStatement.setObject(5, customer.getSalary());
            preparedStatement.setObject(6, customer.getAddress());
            preparedStatement.setObject(7, customer.getCity());
            preparedStatement.setObject(8, customer.getProvince());
            preparedStatement.setObject(9, customer.getPostalCode());

            return 0 < preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        try {
            ResultSet resultSet = DBConection.getInstance().getConnection().prepareStatement("SELECT * FROM customer;").executeQuery();
            if(!customers.isEmpty()){
                customers.clear();
            }
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getNString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                ));
            }

            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE CustID = ?;");
            preparedStatement.setObject(1,id);

            return 0 <preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatement = DBConection.getInstance().getConnection().prepareStatement("UPDATE customer SET CustTitle = ?, CustName = ?, DOB = ?, salary = ?, CustAddress = ?, City = ?, Province = ?, PostalCode = ? WHERE CustID = ?;");

            preparedStatement.setObject(1, customer.getTitle());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getDob());
            preparedStatement.setObject(4, customer.getSalary());
            preparedStatement.setObject(5, customer.getAddress());
            preparedStatement.setObject(6, customer.getCity());
            preparedStatement.setObject(7, customer.getProvince());
            preparedStatement.setObject(8, customer.getPostalCode());
            preparedStatement.setObject(9, customer.getId());

            return 0 < preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateId(){
        return String.format("C%03d", Integer.parseInt(getLastId().substring(1))+1);
    }

    private String getLastId(){
        try {
            ResultSet resultSet = DBConection.getInstance().getConnection().prepareStatement("SELECT CustID FROM customer ORDER BY CustID DESC LIMIT 1;").executeQuery();
            resultSet.next();
            return resultSet.getString("CustID");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
