package com.example.result_service.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
    private Long id;
    private Long studentId;
    private Long examId;
    private String studentName;
    private String examTitle;
    private Integer totalScore;
    private Integer maxScore;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Integer totalQuestions;
    private Double percentage;
    private String result;
    private String evaluatedAtIST;
}