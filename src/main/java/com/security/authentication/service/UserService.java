package com.security.authentication.service;

import com.security.authentication.utils.Mapper;
import com.security.authentication.dto.UserResponseDto;
import com.security.authentication.entity.User;
import com.security.authentication.repository.ProductRepository;
import com.security.authentication.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mapper mapper;
    public UserService(UserRepository userRepository, ProductRepository productRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    public UserResponseDto getUserInfo(Authentication authentication) {

        User authenticatedUser = (User) authentication.getPrincipal();

        userRepository.findById(authenticatedUser.getId()).
                orElseThrow(() -> new DataIntegrityViolationException("User does not exist"));

        return mapper.userToResponseDto(authenticatedUser);

    }

    public ResponseEntity<HttpStatus> deleteProduct(Integer code) {
        var product = productRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Element does not exist"));
        productRepository.delete(product);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> addProductInUserCatalog(Integer code, Authentication authentication) {

        var product = productRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Element does not exist"));

        User authenticatedUser = (User) authentication.getPrincipal();

        product.setUser(authenticatedUser);

        product.setEquipped(true);

        productRepository.save(product);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
