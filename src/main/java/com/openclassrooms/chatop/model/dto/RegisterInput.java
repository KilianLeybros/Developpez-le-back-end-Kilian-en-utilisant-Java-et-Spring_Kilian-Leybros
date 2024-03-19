package com.openclassrooms.chatop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterInput(
        @NotBlank(message="L'adresse email est obligatoire")
        @Size(max=255, message = "L'adresse email contient trop de caractères")
        String email,

        @NotBlank(message="Le nom est obligatoire")
        @Size(max=255, message = "Le nom contient trop de caractères")
        String name,

        @NotBlank(message="Le mot de passe est obligatoire")
        String password) {
}
