package com.example.attempt_service.repository;

import com.example.attempt_service.model.ExamAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository
        extends MongoRepository<ExamAttempt, String> {

    // Check if student already attempted this exam
    Optional<ExamAttempt> findByStudentIdAndExamId(
            Long studentId, Long examId);

    // Get all attempts by student
    List<ExamAttempt> findByStudentId(Long studentId);

    // Get all attempts for an exam
    List<ExamAttempt> findByExamId(Long examId);

    // Get all submitted attempts
    List<ExamAttempt> findByStatus(String status);
}