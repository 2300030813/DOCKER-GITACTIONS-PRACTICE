package com.klef.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.klef.entity.User;
import com.klef.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/userapi")
@CrossOrigin(origins = "http://localhost:5173","http://localhost:7001",allowCredentials = "true")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("❌ Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User loggedInUser = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (loggedInUser != null) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("❌ Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // ✅ Updated to use Integer
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("❌ User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("❌ Error updating profile: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("❌ User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("❌ Error fetching user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
