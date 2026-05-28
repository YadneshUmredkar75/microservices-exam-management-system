package com.example.attempt_service.dto;

import com.example.attempt_service.model.StudentAnswer;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SubmitAnswerRequest {

    @NotNull
    private String attemptId;

    @NotNull
    private List<StudentAnswer> answers;
}