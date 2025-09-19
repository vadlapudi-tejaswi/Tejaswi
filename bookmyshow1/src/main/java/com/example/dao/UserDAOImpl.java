package com.example.dao;

import com.example.dto.UserDTO;
import java.sql.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

public class UserDAOImpl implements UserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/tej?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";  
    private String jdbcPassword = "root";  

    private static final String INSERT_USER_SQL = 
        "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    private static final String SELECT_USER_SQL = 
        "SELECT * FROM users WHERE username = ?";

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDAOImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public boolean registerUser(UserDTO user) {
        try (Connection conn = getConnection();
             PreparedStatement psCheck = conn.prepareStatement(SELECT_USER_SQL)) {

            psCheck.setString(1, user.getUsername());
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                return false; 
            }

            try (PreparedStatement psInsert = conn.prepareStatement(INSERT_USER_SQL)) {
                psInsert.setString(1, user.getUsername());
                
        
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                psInsert.setString(2, hashedPassword);
                
                psInsert.setString(3, user.getEmail());
                int rows = psInsert.executeUpdate();
                return rows > 0;
            }

        } catch (SQLException e) {
            System.out.println("SQL Error in registerUser: " + e.getMessage());
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
                return passwordEncoder.matches(password, storedPassword);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in validateUser: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
