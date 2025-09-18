package com.example.dao;

import com.example.dto.UserDTO;

public interface UserDAO {
    boolean registerUser(UserDTO user);
    boolean validateUser(String username, String password);
}
