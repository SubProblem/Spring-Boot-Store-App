package com.security.authentication.config;

import com.security.authentication.entity.User;
import com.security.authentication.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static com.security.authentication.entity.Permission.*;
import static com.security.authentication.entity.Role.ADMIN;
import static com.security.authentication.entity.Role.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authProvider, UserRepository userRepository, ApplicationConfig applicationConfig) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authProvider = authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()

                .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("/api/v1/user/**").hasAnyRole(USER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyAuthority(USER_READ.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority(USER_CREATE.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyAuthority( USER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAnyAuthority(USER_DELETE.name())


                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
