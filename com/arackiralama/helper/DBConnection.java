package com.arackiralama.helper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connect=null;
    public Connection connectDB(){
        try {
            this.connect= DriverManager.getConnection(config.DB_URL,config.DB_USERNAME,config.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.connect;
    }
    public static Connection getInstance(){
        DBConnection db = new DBConnection();
        return db.connectDB();
    }
}
