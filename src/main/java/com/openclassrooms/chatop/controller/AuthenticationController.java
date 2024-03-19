package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.AuthResponse;
import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.User;
import com.openclassrooms.chatop.services.AuthenticationService;
import com.openclassrooms.chatop.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@Tag(name="Authentication API")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authService;


    @Operation(summary = "Profil", description = "Permet d'accèder au profil de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Profil.class)))})
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
        User registeredUser = authService.register(registerInput);
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
        User registeredUser = authService.login(loginInput);
        String token = jwtService.generateToken(registeredUser);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
