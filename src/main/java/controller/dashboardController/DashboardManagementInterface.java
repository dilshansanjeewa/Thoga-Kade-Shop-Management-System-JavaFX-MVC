package controller.dashboardController;

import javafx.collections.ObservableList;
import model.dto.Item;

public interface DashboardManagementInterface {
    ObservableList<String> getAllCustomerId();
    String[] searchCustomer(String ID);
    ObservableList<String> getAllItemCode();
    Item searchItem(String code);
}
