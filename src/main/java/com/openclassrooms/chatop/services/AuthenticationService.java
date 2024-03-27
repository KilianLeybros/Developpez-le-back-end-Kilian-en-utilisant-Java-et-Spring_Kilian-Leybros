package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.controller.handler.exception.EmailAlreadyExistException;
import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.model.mapper.UserEntityMapper;
import com.openclassrooms.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserEntity register(RegisterInput registerInput) {
        userRepository.findByEmail(registerInput.email()).ifPresent((user) -> {
            throw new EmailAlreadyExistException("Un compte avec "+ user.getEmail() +" comme adresse mail existe déja");
        });
        UserEntity user = UserEntityMapper.fromRegisterInput(registerInput);
        user.setPassword(passwordEncoder.encode(registerInput.password()));
        return user;
    }

    public UserEntity login(LoginInput loginInput){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.email(), loginInput.password()));
        return userRepository.findByEmail(loginInput.email()).orElseThrow();
    }

    public Profil getAuthenticatedUser(){
        UserEntity currentUser = getCurrentUser();
        return UserEntityMapper.toProfil(currentUser);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }


}
