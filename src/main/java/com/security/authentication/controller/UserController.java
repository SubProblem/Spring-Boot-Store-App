package com.security.authentication.controller;


import com.security.authentication.dto.UserResponseDto;
import com.security.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponseDto getUserInfo(Authentication authentication) {
        return userService.getUserInfo(authentication);
    }

    @PostMapping("/product/{code}")
    public ResponseEntity<HttpStatus> addProductInList(@PathVariable Integer code, Authentication authentication) {
        return userService.addProductInUserCatalog(code, authentication);
    }

    @DeleteMapping("/product/{code}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer code, Authentication authentication) {
        return userService.deleteProduct(code);
    }


}
