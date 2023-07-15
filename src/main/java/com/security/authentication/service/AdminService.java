package com.security.authentication.service;

import com.security.authentication.utils.Mapper;
import com.security.authentication.dto.ProductRequestDto;
import com.security.authentication.dto.ProductResponseDto;
import com.security.authentication.dto.UserResponseDto;
import com.security.authentication.entity.Product;
import com.security.authentication.entity.User;
import com.security.authentication.repository.ProductRepository;
import com.security.authentication.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mapper mapper;

    public AdminService(UserRepository userRepository, ProductRepository productRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<UserResponseDto> users() {
        var users = userRepository.findAll();
        return users.stream()
                .map(this::userToResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> products() {
        var products = productRepository.findAll();

        return products.stream()
                .map(this::productToResponseDto)
                .toList();

    }

    public ResponseEntity<HttpStatus> addProduct(ProductRequestDto productRequestDto) {
        var product = Product.builder()
                .code(productRequestDto.code())
                .price(productRequestDto.price())
                .description(productRequestDto.description())
                .name(productRequestDto.name())
                .build();
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> deleteProduct(Integer code) {
        var product = productRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Product does not exist"));
        productRepository.delete(product);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> deleteUser(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User does not exist"));
        userRepository.delete(user);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    private UserResponseDto userToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                mapper.mapper(user.getProducts())
        );
    }

    private ProductResponseDto productToResponseDto(Product product) {
        return new ProductResponseDto(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
