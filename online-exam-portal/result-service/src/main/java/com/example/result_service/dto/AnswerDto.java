package com.example.result_service.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long questionId;
    private String selectedAnswer;
    private String correctAnswer;
}