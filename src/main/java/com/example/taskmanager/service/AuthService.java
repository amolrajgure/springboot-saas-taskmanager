package com.example.taskmanager.service;

import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.dto.RegisterRequest;
import com.example.taskmanager.exception.DuplicateEntryException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Handle registration: encode password, save user with role ROLE_USER
 * Handle login: authenticate username/password and return JWT token
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateEntryException("Username already taken: " + request.getUsername());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role("ROLE_USER")
                .enabled(true)
                .build();

        userRepository.save(user);

        String token = jwtTokenProvider.createToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.createToken(request.getUsername());
        return new AuthResponse(token);
    }
}
