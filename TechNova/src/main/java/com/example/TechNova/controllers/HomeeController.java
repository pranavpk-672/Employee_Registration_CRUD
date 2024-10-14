package com.example.TechNova.controllers;

import com.example.TechNova.models.User;
import com.example.TechNova.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeeController {

    private final UserService userService;

    public HomeeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        // Get the logged-in user's email (the principal's username)
        String email = authentication.getName();
        
        // Fetch the user details from the database using the email
        User loggedInUser = userService.getUserByEmail(email);
        
        // Add the user object to the model to pass to the view
        model.addAttribute("user", loggedInUser);

        return "home";  // View name: home.html
    }
    
    
    
    
    
    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {
        // Get the logged-in user's email (the principal's username)
        String email = authentication.getName();
        
        // Fetch the user details from the database using the email
        User loggedInUser = userService.getUserByEmail(email);
        
        // Add the user object to the model to pass to the view
        model.addAttribute("user", loggedInUser);

        return "profile";  // View name: profile.html
    }
}
