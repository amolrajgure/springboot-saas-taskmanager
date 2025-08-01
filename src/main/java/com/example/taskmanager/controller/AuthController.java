package com.example.taskmanager.controller;

import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.dto.RegisterRequest;
import com.example.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Maps BadCredentialsException to a 401 Unauthorized HTTP status code.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public void handleUnathorized(Exception ex) {
        //logger.error("Exception is: ", ex);
        // return empty 404
        System.out.println("Exception " + ex.getMessage());
    }
}
