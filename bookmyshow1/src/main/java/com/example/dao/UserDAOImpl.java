package com.example.dao;

import com.example.dto.UserDTO;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/your_database";
    private String jdbcUsername = "root";  // Your DB username
    private String jdbcPassword = "password";  // Your DB password

    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    private static final String SELECT_USER_SQL = "SELECT * FROM users WHERE username = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public boolean registerUser(UserDTO user) {
        try (Connection conn = getConnection();
             PreparedStatement psCheck = conn.prepareStatement(SELECT_USER_SQL);
             PreparedStatement psInsert = conn.prepareStatement(INSERT_USER_SQL)) {

            // Check if username exists
            psCheck.setString(1, user.getUsername());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                return false; // username already exists
            }

            // Insert new user
            psInsert.setString(1, user.getUsername());
            psInsert.setString(2, user.getPassword());
            psInsert.setString(3, user.getEmail());
            int rows = psInsert.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validateUser(String username, String password) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
