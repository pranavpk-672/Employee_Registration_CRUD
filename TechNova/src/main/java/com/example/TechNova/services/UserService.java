package com.example.TechNova.services;

import com.example.TechNova.models.User;
import com.example.TechNova.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;




import java.time.ZoneId;
import java.util.Date;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    

    public List<User> getAllUsers() {
        LocalDate filterDate = LocalDate.of(2024, 10, 5);
        return userRepository.findAll().stream()
            .filter(user -> {
                java.sql.Date dateOfJoined = (java.sql.Date) user.getDateOfJoined(); 
                if (dateOfJoined != null) {
                    LocalDate localDateOfJoined = dateOfJoined.toLocalDate();
                    return localDateOfJoined.isAfter(filterDate);
                }
                return false;
            })
            .collect(Collectors.toList());
    }


   
            
    
    
    

    // Register a new user with password encoding
    public void registerUser(User user) {
        // Check if the email is already in use
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email is already in use");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update existing user details
    public User updateUser(User user) {
        // Find the existing user by ID
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update the user’s details (excluding password changes)
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setDob(user.getDob());

        // Save and return the updated user
        return userRepository.save(existingUser);
    }
}
