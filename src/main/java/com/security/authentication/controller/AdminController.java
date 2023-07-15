package com.security.authentication.controller;

import com.security.authentication.dto.ProductRequestDto;
import com.security.authentication.dto.ProductResponseDto;
import com.security.authentication.dto.UserResponseDto;
import com.security.authentication.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUser() {
        return adminService.users();
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProduct() {
        return adminService.products();
    }

    @PostMapping("/products")
    public ResponseEntity<HttpStatus> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return adminService.addProduct(productRequestDto);
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody String email) {
        return adminService.deleteUser(email);
    }

    @DeleteMapping("/products/{code}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer code) {
        return adminService.deleteProduct(code);
    }
}
