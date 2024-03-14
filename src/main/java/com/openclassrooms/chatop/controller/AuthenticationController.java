package com.openclassrooms.chatop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    @GetMapping("/me")
    public String profil(){
        return "Profil";
    }


    @PostMapping("/register")
    public String register(){
        return "Register";
    }


    @PostMapping("/login")
    public String login(){
        return "Login";
    }
}
