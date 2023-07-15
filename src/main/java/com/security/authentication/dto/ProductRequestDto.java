package com.security.authentication.dto;

import java.math.BigDecimal;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Integer code
) {
}
