package com.openclassrooms.chatop.model.dto;

import jakarta.validation.constraints.Email;

public record LoginInput(@Email(message = "Le format de l'adresse mail n'est pas valide") String email, String password) {
}
