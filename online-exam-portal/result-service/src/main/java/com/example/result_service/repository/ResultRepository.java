package com.example.result_service.repository;

import com.example.result_service.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Optional<Result> findByAttemptId(String attemptId);
    List<Result> findByStudentId(Long studentId);
    List<Result> findByExamId(Long examId);
    Optional<Result> findByStudentIdAndExamId(
            Long studentId, Long examId);
}