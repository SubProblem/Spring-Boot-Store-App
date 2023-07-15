package com.security.authentication.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
