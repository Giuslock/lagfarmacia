package org.univaq.oop.business.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {


    public static Connection getConnection() {
        Properties props = AppConfigReader.getProps();
        String host = "jdbc:mysql://localhost:3306/" + props.getProperty("DB_DATABASE") + "?serverTimezone=UTC";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    host,
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
        } catch (SQLException throwables) {
            System.err.println("COULD NOT ESTABLISH A CONNECTION");
            throwables.printStackTrace();
        }

        return connection;
    }

}
