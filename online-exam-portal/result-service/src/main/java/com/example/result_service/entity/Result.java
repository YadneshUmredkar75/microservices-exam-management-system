package com.example.result_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long examId;

    // MongoDB attempt ID from attempt-service
    @Column(nullable = false)
    private String attemptId;

    private String studentName;
    private String examTitle;

    @Column(nullable = false)
    private Integer totalScore;

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private Integer correctAnswers;

    @Column(nullable = false)
    private Integer wrongAnswers;

    @Column(nullable = false)
    private Integer totalQuestions;

    // Percentage score
    @Column(nullable = false)
    private Double percentage;

    // Pass or Fail (passing mark = 40%)
    @Column(nullable = false)
    private String result;

    // IST timezone stored
    @Column(nullable = false)
    private LocalDateTime evaluatedAt;

    // IST formatted string for display
    // Same as your formatIST() helper in MERN
    @Column
    private String evaluatedAtIST;
}