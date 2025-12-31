package com.api.utils;

import java.sql.*;
import java.util.*;

public class DBUtils {
    private Connection connection;
    private ConfigReader config;

    public DBUtils(ConfigReader config) {
        this.config = config;
    }

    public void connectToDB() {
        try {
            String url = config.getProperty("db.url");
            String user = config.getProperty("db.user");
            String password = config.getProperty("db.password");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }

    public Map<String, String> getRowData(String query, Object... params) {
        Map<String, String> rowData = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) stmt.setObject(i + 1, params[i]);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            if (rs.next()) {
                for (int col = 1; col <= meta.getColumnCount(); col++) {
                    rowData.put(meta.getColumnName(col), rs.getString(col));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
        return rowData;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error closing DB connection", e);
        }
    }
}
