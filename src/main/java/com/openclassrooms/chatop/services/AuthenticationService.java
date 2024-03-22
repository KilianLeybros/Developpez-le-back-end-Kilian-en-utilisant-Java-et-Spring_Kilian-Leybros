package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.UserEntity;
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


    public UserEntity register(RegisterInput registerInput){
        UserEntity user = new UserEntity()
                .setName(registerInput.name())
                .setEmail(registerInput.email())
                .setPassword(passwordEncoder.encode(registerInput.password()))
                .setCreatedAt(Timestamp.from(Instant.now()));
        return userRepository.save(user);

    }

    public UserEntity login(LoginInput loginInput){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInput.login(), loginInput.password()));
        return userRepository.findByEmail(loginInput.login()).orElseThrow();
    }

    public Profil getAuthenticatedUser(){
        UserEntity currentUser = getCurrentUser();
        return new Profil(
                currentUser.getId(),
                currentUser.getName(),
                currentUser.getEmail(),
                currentUser.getCreatedAt().toLocalDateTime().toLocalDate(),
                currentUser.getUpdatedAt() != null ?  currentUser.getUpdatedAt().toLocalDateTime().toLocalDate() : null);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }


}
