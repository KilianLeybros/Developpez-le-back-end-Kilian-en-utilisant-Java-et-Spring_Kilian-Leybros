package com.openclassrooms.chatop.model.dto;

import java.math.BigDecimal;

public record Rental(Long id, String name, Long surface, Long price, String picture, String description) {
}
