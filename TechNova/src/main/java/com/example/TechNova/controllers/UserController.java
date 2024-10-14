package com.example.TechNova.controllers;

import com.example.TechNova.models.User;
import com.example.TechNova.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register";
        }

        try {
            userService.registerUser(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        return "redirect:/register-success"; // Redirect to a success page after registration
    }

    // Show registration success page
    @GetMapping("/register-success")
    public String registerSuccess() {
        return "register-success"; // Returns the success page view
    }

    // Show the contact page
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Returns the contact page view (contact.html)
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about"; // Returns the about page view (about.html)
    }

    @GetMapping("/services")
    public String showServicePage() {
        return "services"; // Returns the services page view (services.html)
    }
    
    
    
    @GetMapping("/users_list")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users_list";
        
    }
    
    
    
       

}
