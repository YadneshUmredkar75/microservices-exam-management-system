package com.example.auth_service.controller;

import com.example.auth_service.dto.JwtResponse;
import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.entity.Role;
import com.example.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role) {
        return ResponseEntity.ok(authService.register(name, email, password, role));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running!");
    }
}