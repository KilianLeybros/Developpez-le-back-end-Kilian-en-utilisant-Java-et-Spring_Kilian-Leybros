package com.openclassrooms.chatop.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Rental(
        Long id,
        String name,
        Long surface,
        Long price,
        String picture,
        String description,
        Long owner_id,

        LocalDate created_at,
        LocalDate updated_at) {
}
