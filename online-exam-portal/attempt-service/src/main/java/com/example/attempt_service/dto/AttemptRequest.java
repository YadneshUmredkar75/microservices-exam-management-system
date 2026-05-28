package com.example.attempt_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttemptRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotNull(message = "Admin ID is required")
    private Long adminId;

    private String studentName;
    private String examTitle;
}