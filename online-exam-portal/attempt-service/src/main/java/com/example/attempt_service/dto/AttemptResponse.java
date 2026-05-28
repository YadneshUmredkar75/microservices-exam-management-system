package com.example.attempt_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttemptResponse {
    private String message;
    private String attemptId;
    private String status;
}