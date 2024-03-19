package com.openclassrooms.chatop.model.dto;

import java.time.LocalDate;
import java.util.Optional;

public record Profil(Long id, String name, String email, LocalDate created_at, LocalDate updated_at) {
}
