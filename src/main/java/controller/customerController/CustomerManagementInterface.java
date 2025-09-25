package controller.customerController;

import javafx.collections.ObservableList;
import model.dto.Customer;

public interface CustomerManagementInterface {
    String getNewId();
    boolean addCustomer(Customer customer);
    ObservableList<Customer> getAllCustomers();
    boolean deleteCustomer(String id);
    boolean updateCustomer(Customer customer);
}
