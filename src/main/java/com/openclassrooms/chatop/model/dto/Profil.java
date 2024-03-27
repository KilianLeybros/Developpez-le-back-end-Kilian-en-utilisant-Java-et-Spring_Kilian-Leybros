package com.openclassrooms.chatop.model.dto;

import java.time.LocalDate;

public record Profil(Long id, String name, String email, LocalDate created_at, LocalDate updated_at) {
}
