package com.example.proctor_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FaceEventRequest {

    @NotNull
    private String attemptId;

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;

    // FACE_DETECTED, FACE_MISSING,
    // MULTIPLE_FACES, TAB_SWITCH
    @NotBlank
    private String eventType;

    private String message;
}