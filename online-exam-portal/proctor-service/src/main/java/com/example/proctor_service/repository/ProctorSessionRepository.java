package com.example.proctor_service.repository;

import com.example.proctor_service.model.ProctorSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProctorSessionRepository
        extends MongoRepository<ProctorSession, String> {

    Optional<ProctorSession> findByAttemptId(String attemptId);
    Optional<ProctorSession> findByStudentIdAndExamId(
            Long studentId, Long examId);
    List<ProctorSession> findByExamId(Long examId);
    List<ProctorSession> findByIsTerminatedTrue();
}