package com.security.authentication.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String name,
        String description,
        BigDecimal price
) {
}
