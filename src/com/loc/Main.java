package com.loc;

import java.sql.*;
import java.io.IOException;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";
    static final String DB_URL = "jdbc:mysql://localhost:3306/register_db";
    static final String USER = "root";
    static final String PASS = "1234";
    static Connection connection = null;
    static Statement statement = null;

    public static void establishConnection() throws SQLException, ClassNotFoundException{
        if (connection != null) return;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
    }

    public static ResultSet getResultSet(String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public static int LastInsertId() throws SQLException{
        ResultSet resultSet = getResultSet("SELECT LAST_INSERT_ID();");
        resultSet.next();
        return resultSet.getInt("LAST_INSERT_ID()");
    }

    public static void main(String[] args) throws IOException, SQLException {
        try {
            establishConnection();
            System.out.println("mainnnnnnnnnnnnnn functionnnnnnnnn");
        }
        catch (Exception exception){
            exception.printStackTrace();
            System.out.println("catch mainnnnnnnn");
}

        finally {
            System.out.println("Connecting to jersey database");
        }
    }
}

