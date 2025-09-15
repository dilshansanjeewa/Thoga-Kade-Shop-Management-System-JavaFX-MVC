package controller.dashboardController;

import javafx.collections.ObservableList;

public interface DashboardManagementInterface {
    ObservableList<String> getAllCustomerId();
    String[] getCustomer(String ID);
    ObservableList<String> getAllItemCode();
}
