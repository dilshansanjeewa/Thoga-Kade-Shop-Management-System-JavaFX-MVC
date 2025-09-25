package controller.dashboardController;

import javafx.collections.ObservableList;
import model.dto.Item;
import model.dto.Order;

public interface DashboardManagementInterface {
    ObservableList<String> getAllCustomerId();

    String[] searchCustomer(String ID);

    ObservableList<String> getAllItemCode();

    Item searchItem(String code);

    String getLastOrderId();

    boolean addOrder(Order order);
}