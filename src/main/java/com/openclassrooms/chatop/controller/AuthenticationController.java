package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.User;
import com.openclassrooms.chatop.services.AuthenticationService;
import com.openclassrooms.chatop.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authService;


    @GetMapping("/me")
    public ResponseEntity<User> me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterInput registerInput){
        User registeredUser = authService.register(registerInput);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInput loginInput){
        User registeredUser = authService.login(loginInput);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok(token);
    }
}
