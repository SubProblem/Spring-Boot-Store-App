package com.security.authentication;

import com.security.authentication.config.ApplicationConfig;
import com.security.authentication.dto.RegisterRequest;
import com.security.authentication.entity.Role;
import com.security.authentication.service.AuthenticationService;
import com.security.authentication.service.RegisterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.security.authentication.entity.Role.ADMIN;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}


}
