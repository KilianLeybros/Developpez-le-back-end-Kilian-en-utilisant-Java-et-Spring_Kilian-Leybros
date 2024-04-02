package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.AuthResponse;
import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.services.IAuthenticationService;
import com.openclassrooms.chatop.services.IJwtService;
import com.openclassrooms.chatop.services.implementations.AuthenticationService;
import com.openclassrooms.chatop.services.implementations.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@Tag(name="Authentication API")
@RestController
public class AuthenticationController {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IAuthenticationService authService;


    @Operation(summary = "Profil", description = "Permet d'accèder au profil de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Profil.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping("/me")
    public ResponseEntity<Profil> me(){
        return ResponseEntity.ok(authService.getAuthenticatedUser());
    }


    @Operation(summary = "Register", description = "Permet d'enregistrer un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur enregistré avec succès",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterInput registerInput){
        UserEntity registeredUser = authService.register(registerInput);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok(new AuthResponse(token));
    }


    @Operation(summary = "Login", description = "Permet de se connecter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté avec succès",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginInput loginInput){
        UserEntity registeredUser = authService.login(loginInput);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
