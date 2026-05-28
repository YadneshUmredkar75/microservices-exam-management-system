package com.example.attempt_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswer {

    private Long questionId;

    // Answer student selected: "A", "B", "C", or "D"
    private String selectedAnswer;

    // Will be filled when result is calculated
    private Boolean isCorrect;
}