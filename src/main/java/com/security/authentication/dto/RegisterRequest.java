package com.security.authentication.dto;

import com.security.authentication.entity.Role;
import lombok.Builder;

@Builder
public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
