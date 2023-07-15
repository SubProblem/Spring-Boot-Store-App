package com.security.authentication.service;

import com.security.authentication.dto.ProductRequestDto;
import com.security.authentication.dto.ProductResponseDto;
import com.security.authentication.entity.Product;
import com.security.authentication.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<HttpStatus> addProduct(ProductRequestDto productRequestDto) {
        var product = Product.builder()
                .price(productRequestDto.price())
                .name(productRequestDto.name())
                .description(productRequestDto.description())
                .code(productRequestDto.code())
                .build();

        productRepository.save(product);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> deleteProduct(Integer code) {
        var product = productRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Element does not exist"));
        productRepository.delete(product);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
