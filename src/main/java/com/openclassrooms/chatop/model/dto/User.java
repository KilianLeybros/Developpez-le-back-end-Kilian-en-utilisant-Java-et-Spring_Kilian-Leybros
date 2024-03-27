package com.openclassrooms.chatop.model.dto;

import java.time.LocalDate;

public record User(Long id, String name, String email, LocalDate created_at, LocalDate updated_at) {
}
