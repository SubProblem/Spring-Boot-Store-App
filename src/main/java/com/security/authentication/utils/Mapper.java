package com.security.authentication.utils;

import com.security.authentication.dto.ProductResponseDto;
import com.security.authentication.dto.UserResponseDto;
import com.security.authentication.entity.Product;
import com.security.authentication.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {
    public UserResponseDto userToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                mapper(user.getProducts())
        );
    }

    public ProductResponseDto productToResponseDto(Product product) {
        return new ProductResponseDto(
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    public List<ProductResponseDto> mapper(List<Product> products) {
        return products.stream()
                .map(this::productToResponseDto)
                .toList();
    }
}
