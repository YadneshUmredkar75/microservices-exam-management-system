package com.example.result_service.dto;

import com.example.result_service.dto.AnswerDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ResultRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private Long examId;

    @NotNull
    private String attemptId;

    private String studentName;
    private String examTitle;

    @NotNull
    private Integer marksPerQuestion;

    private List<AnswerDto> answers;
}