package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.User;
import com.openclassrooms.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    public User register(RegisterInput registerInput){
        User user = new User()
                .setName(registerInput.name())
                .setEmail(registerInput.email())
                .setPassword(passwordEncoder.encode(registerInput.password()))
                .setCreatedAt(Timestamp.from(Instant.now()));
        return userRepository.save(user);

    }

    public User login(LoginInput loginInput){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.login(), loginInput.password()));
        return userRepository.findByEmail(loginInput.login()).orElseThrow();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }


}
