package com.example.controller;

import com.example.dao.UserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.UserDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserDAO userDAO = new UserDAOImpl();

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO user, Model model) {
        boolean registered = userDAO.registerUser(user);
        if (registered) {
            model.addAttribute("message", "Registration successful. Please login.");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        boolean valid = userDAO.validateUser(username, password);
        if (valid) {
            model.addAttribute("username", username);
            return "welcome";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }
}
