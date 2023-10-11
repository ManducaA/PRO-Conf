package it.informatica.configuratore.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    Connection connection;

    public void startConnection() throws SQLException {
        String url = "jdbc:sqlite:data.db";
        connection = DriverManager.getConnection(url);
    }

    public Connection getConnection(){
        return connection;
    }

}
