package com.openclassrooms.chatop.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateRentalInput(
        @NotEmpty(message = "Le nom est obligatoire")
        @Size(max=255 ,message = "Le nom ne peut pas depasser 255 caractères")
        String name,
        @NotEmpty(message = "La surface est obligatoire")
        @Min(value = 1, message = "La surface doit être supérieur à 0")
        @Digits(integer = 10,fraction = 0, message="La surface doit être un nombre inferieur à 999999999")
        String surface,
        @NotEmpty(message = "La prix est obligatoire")
        @Min(value = 1, message = "La prix doit être supérieur à 0")
        @Digits(integer = 10,fraction = 0, message="La prix doit être un nombre inferieur à 999999999")
        String price,

        @NotEmpty(message = "La description est obligatoire")
        @Size(max=2000 ,message = "La description ne peut pas depasser 2000 caractères")
        String description
) {
}
