package com.example.TechNova.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom_login")
public class LoginController {

    @GetMapping
    public String customLogin() {
        return "custom_login";
    }
}
