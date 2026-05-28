package com.example.proctor_service.repository;

import com.example.proctor_service.model.FaceEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FaceEventRepository
        extends MongoRepository<FaceEvent, String> {

    List<FaceEvent> findByAttemptId(String attemptId);
    List<FaceEvent> findByStudentId(Long studentId);
    List<FaceEvent> findByExamId(Long examId);
    List<FaceEvent> findByAttemptIdAndEventType(
            String attemptId, String eventType);
    long countByAttemptIdAndEventType(
            String attemptId, String eventType);
}