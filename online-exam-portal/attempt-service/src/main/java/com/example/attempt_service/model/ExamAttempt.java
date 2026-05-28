package com.example.attempt_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

// Compound unique index → one attempt per student per exam
// Same as your MERN ExamAttempt model
@Document(collection = "exam_attempts")
@CompoundIndex(
        def = "{'studentId': 1, 'examId': 1}",
        unique = true
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttempt {

    @Id
    private String id;

    private Long studentId;
    private Long examId;
    private Long adminId;

    private String studentName;
    private String examTitle;

    // Answers submitted by student
    private List<StudentAnswer> answers;

    // Status: IN_PROGRESS, SUBMITTED, EVALUATED
    private String status;

    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;

    // Score filled by result-service later
    private Integer totalScore;
    private Integer maxScore;

    // Face detection flag from proctor-service
    private Boolean faceDetected = true;
    private Integer faceAbsenceCount = 0;
}