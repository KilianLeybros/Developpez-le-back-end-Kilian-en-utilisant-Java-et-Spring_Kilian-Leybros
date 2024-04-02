package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.User;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.services.IUserService;
import com.openclassrooms.chatop.services.implementations.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API")
public class UserController {

    @Autowired
    private IUserService userService;


    @Operation(summary = "Find User", description = "Permet de récupérer un utilisateur via son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "L'utilisateur avec l'id spécifié n'a pas été trouvé",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        UserEntity user = userService.findUserById(id);
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt().toLocalDateTime().toLocalDate(),
                Optional.ofNullable(user.getUpdatedAt()).map(timestamp -> timestamp.toLocalDateTime().toLocalDate()).orElse(null)
        );
    }
}
