package com.example.gadkiyabram.gyrodatalithium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    DataBaseHelper dbHelper;

    private final String LOG_TAG = "mylogs";
    private static final String APP_SETTINGS = "U_SETTING";
    private Connection dbConnection;

    private String serverName, ipAddress, port, dbName;

    private String connectString(String serverName,
                                String ipAddress,
                                String port,
                                String dbName){
        StringBuilder connBuilder = new StringBuilder();
        connBuilder.append("jdbc:jtds:")
                .append(serverName)
                .append("://")
                .append(ipAddress)
                .append(":")
                .append(port)
                .append(";databaseName=")
                .append(dbName)
                .append(";loginTimeout=10;socketTimeout=10");
        return connBuilder.toString();
    }

    protected Connection getConnection(){

        try{
            dbConnection = DriverManager.getConnection(connectString(serverName, ipAddress, port, dbName));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }

}
