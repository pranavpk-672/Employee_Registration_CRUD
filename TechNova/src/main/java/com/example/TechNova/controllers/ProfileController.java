package com.example.TechNova.controllers;

import com.example.TechNova.models.User;
import com.example.TechNova.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    // Show the edit profile form
    @GetMapping("/edit_profile")
    public String showEditProfileForm(Model model, Principal principal) {
        // Fetch the user by email (which is the username in Principal)
        User user = userService.getUserByEmail(principal.getName());
        
        // Check if the user is found
        if (user == null) {
            // Handle the case when the user is not found (you may want to log this)
            return "error"; // Redirect to an error page or handle it appropriately
        }

        // Add the user object to the model so that it can be pre-filled in the form
        model.addAttribute("user", user);
        return "edit_profile"; // Points to edit_profile.html (your view file)
    }

    // Handle form submission and update profile details
    @PostMapping("/edit_profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Principal principal) {
        // Fetch the current user from DB
        User existingUser = userService.getUserByEmail(principal.getName());

        if (existingUser == null) {
            // Handle the case when the user is not found
            return "error"; // Redirect to an error page or handle it appropriately
        }

        // Update the existing user's details with new information
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setDob(updatedUser.getDob());

        // Save the updated user back to the database
        userService.updateUser(existingUser);

        return "redirect:/profile"; // Redirect to home or another page after updating
    }
}
