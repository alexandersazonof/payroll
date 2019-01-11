package by.etc.payroll.dao.util;

import java.util.ResourceBundle;

public interface DatabaseConstants {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("configuration.database");


    String DRIVER = resourceBundle.getString("db.driver");
    String URL = resourceBundle.getString("db.url");
    String LOGIN = resourceBundle.getString("db.login");
    String PASSWORD = resourceBundle.getString("db.password");
    int POOL_SIZE = Integer.valueOf(resourceBundle.getString("db.poolsize"));

}
