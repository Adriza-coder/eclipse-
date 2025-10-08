package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER NEW USER
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Check if username already exists
            if (userRepository.existsByUsername(user.getUsername())) {
                response.put("error", "Username already exists!");
                return ResponseEntity.status(400).body(response);
            }

            // Set default role if none provided
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add("USER");
                user.setRoles(roles);
            }

            User savedUser = userRepository.save(user);
            response.put("message", "Registration successful!");
            response.put("user", savedUser);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // LOGIN USER
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginData) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (loginData == null || loginData.getUsername() == null || loginData.getPassword() == null) {
                response.put("error", "Invalid login data!");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<User> optionalUser = userRepository.findFirstByUsername(loginData.getUsername());

            if (optionalUser.isEmpty()) {
                response.put("error", "Invalid username or password");
                return ResponseEntity.status(401).body(response);
            }

            User existingUser = optionalUser.get();

            if (existingUser.getPassword().equals(loginData.getPassword())) {
                String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRoles());
                response.put("token", token);
                response.put("username", existingUser.getUsername());
                response.put("roles", existingUser.getRoles());
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Invalid username or password");
                return ResponseEntity.status(401).body(response);
            }

        } catch (Exception e) {
            response.put("error", "Login failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
