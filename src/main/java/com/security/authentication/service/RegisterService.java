package com.security.authentication.service;

import com.security.authentication.config.JwtService;
import com.security.authentication.dto.AuthenticationResponse;
import com.security.authentication.dto.RegisterRequest;
import com.security.authentication.entity.Role;
import com.security.authentication.entity.User;
import com.security.authentication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.security.authentication.entity.Role.ADMIN;
import static com.security.authentication.entity.Role.USER;

@Service
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public RegisterService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);

    }
}
