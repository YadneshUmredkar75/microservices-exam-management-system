package com.example.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
 private String token;
 private String role;
 private String name;
 private Long userId;
}
