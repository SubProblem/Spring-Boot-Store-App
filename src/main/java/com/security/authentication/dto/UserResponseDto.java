package com.security.authentication.dto;

import com.security.authentication.entity.Product;

import java.util.List;

public record UserResponseDto(
        Integer id,
        String firstname,
        String lastname,
        List<ProductResponseDto> products
) {
}
