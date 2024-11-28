package com.api.book.controller;


import com.api.book.dto.UserRegistrationDTO;
import com.api.book.entity.User;
import com.api.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Register a new user with default USER role
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRoles(Set.of("ROLE_USER")); // Default role as USER
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Register a new admin with ADMIN role
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegistrationDTO adminDTO) {
        User admin = new User();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(adminDTO.getPassword());
        admin.setRoles(Set.of("ROLE_ADMIN")); // Assign ADMIN role
        userService.registerUser(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

 /*   @GetMapping("/login")
    public ResponseEntity<String> login() {
        // If authentication is successful, this will return a 200 OK.
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        // Spring Security handles logout automatically when the endpoint is hit.
        return ResponseEntity.ok("Logout successful");
    }*/
}
