package com.example.proctor_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessionRequest {

    @NotNull
    private String attemptId;

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;

    private String studentName;
}