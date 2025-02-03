package com.example.JavaFXLogin;

import java.sql.*;


public class DBConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "login_db";
        String databaseUser = "justino";
        String databasePass = "123321";
        String url = "jdbc:postgresql://localhost/" + databaseName;

        try{
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePass);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
