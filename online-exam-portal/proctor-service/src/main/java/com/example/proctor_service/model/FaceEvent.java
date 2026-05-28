package com.example.proctor_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "face_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceEvent {

    @Id
    private String id;

    @Indexed
    private String attemptId;

    private Long studentId;
    private Long examId;

    // FACE_DETECTED, FACE_MISSING, MULTIPLE_FACES,
    // TAB_SWITCH, EXAM_STARTED, EXAM_ENDED
    private String eventType;

    private String message;

    // IST time of event
    private LocalDateTime eventTime;
    private String eventTimeIST;

    // Warning count at time of event
    private Integer warningCount;
}