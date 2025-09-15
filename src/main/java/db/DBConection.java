package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConection {

    private static DBConection instance;
    private final Connection connection;

    private DBConection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thogakade", "root", "12345");
    }

    public static DBConection getInstance() throws SQLException {
        if(instance == null) instance = new DBConection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
