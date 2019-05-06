package com.loc;

import java.sql.*;

import org.json.JSONArray;

import java.io.IOException;

public class Util {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";
    static final String DB_URL = "jdbc:mysql://localhost:3306/register_db?useSSL=false";
    static final String USER = "root";
    static final String PASS = "loc123";

    public static Connection establishConnection() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		return DriverManager.getConnection(DB_URL, USER, PASS);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public static int LastInsertId() throws SQLException{
    	int num = -1;
    	try (Connection connection = establishConnection()){
    		Statement statement = connection.createStatement();
    		try (ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID();")){
    			resultSet.next();
    			num = resultSet.getInt("LAST_INSERT_ID()");
    			resultSet.close();
    		}
    		connection.close();
    	}
    	return num;
    }
    
    public static JSONArray getQueryArray(String query) throws Exception{
    	JSONArray jsonArray = null;
    	try (Connection connection = establishConnection()){
    		Statement statement = connection.createStatement();
    		try (ResultSet resultSet = statement.executeQuery(query)){
    			jsonArray = JsonSerializer.convertToJSON(resultSet);
    			resultSet.close();
    		}
    		connection.close();
    	}
    	return jsonArray;
    }
    
    public static void executeQuery(String query) throws Exception{
    	try (Connection connection = establishConnection()){
    		connection.createStatement().executeQuery(query).close();
    		connection.close();
    	}
    }
    
    public static boolean isQualified(int studentId, int moduleId) throws Exception{
    	float percent = 0;
    	try (Connection connection = establishConnection()){
    		Statement statement = connection.createStatement();
    		String query = String.format("CALL GetAttendancePercentByStudentIdModuleId(%d, %d);", studentId, moduleId);
    		try (ResultSet resultSet = statement.executeQuery(query)){
    			resultSet.next();
    			percent = resultSet.getFloat("Percent");
    			resultSet.close();
    		}
    		connection.close();
    	}
    	return percent >= 80;
    }
}

