package com.example.exam_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotNull
    @Min(value = 1, message = "Marks per question must be at least 1")
    private Integer marksPerQuestion;  // locked after creation

    @NotNull
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private List<QuestionRequest> questions;
}