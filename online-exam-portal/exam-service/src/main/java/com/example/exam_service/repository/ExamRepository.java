package com.example.exam_service.repository;

import com.example.exam_service.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByIsActiveTrue();
    List<Exam> findByCreatedByAdminId(Long adminId);
    List<Exam> findBySubject(String subject);
}
