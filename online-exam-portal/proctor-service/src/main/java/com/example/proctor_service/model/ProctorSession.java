package com.example.proctor_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// One session per student per exam
@Document(collection = "proctor_sessions")
@CompoundIndex(
        def = "{'studentId': 1, 'examId': 1}",
        unique = true
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProctorSession {

    @Id
    private String id;

    private String attemptId;
    private Long studentId;
    private Long examId;
    private String studentName;

    // ACTIVE, TERMINATED, COMPLETED
    private String status;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    // Counts
    private Integer faceAbsenceCount = 0;
    private Integer multipleFaceCount = 0;
    private Integer tabSwitchCount    = 0;
    private Integer totalWarnings     = 0;

    // Auto-terminate if warnings exceed limit
    private Boolean isTerminated = false;
    private String  terminationReason;

    // Max warnings before auto-terminate
    // Same concept as your MERN face detection threshold
    private Integer warningLimit = 5;
}