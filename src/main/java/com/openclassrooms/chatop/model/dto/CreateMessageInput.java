package com.openclassrooms.chatop.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record CreateMessageInput(
        @NotEmpty(message = "Le champ message est obligatoire")
        @Size(max = 2000, message = "Le message ne peut pas dépasser 2000 caractères")
        String message,
        @NonNull
        Long user_id,
        @NonNull
        Long rental_id) {
}
