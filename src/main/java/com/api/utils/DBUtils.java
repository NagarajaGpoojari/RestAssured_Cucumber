package com.api.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBUtils {
	
	    private static Connection connection;

	    public static void connectToDB() {
	        try {
	            String url = ConfigReader.getProperty("db.url");
	            String user = ConfigReader.getProperty("db.user");
	            String password = ConfigReader.getProperty("db.password");
	            connection = DriverManager.getConnection(url, user, password);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static Map<String, String> getUserFromDB(int userId) {
	        Map<String, String> userData = new HashMap<>();
	        try {
	            String query = "SELECT name, job FROM users WHERE id = ?";
	            PreparedStatement stmt = connection.prepareStatement(query);
	            stmt.setInt(1, userId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                userData.put("name", rs.getString("name"));
	                userData.put("job", rs.getString("job"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return userData;
	    }

	    public static void closeConnection() {
	        try {
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

