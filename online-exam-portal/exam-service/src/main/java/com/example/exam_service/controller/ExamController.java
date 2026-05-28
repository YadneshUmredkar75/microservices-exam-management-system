package com.example.exam_service.controller;

import com.example.exam_service.dto.ExamRequest;
import com.example.exam_service.entity.Exam;
import com.example.exam_service.entity.Question;
import com.example.exam_service.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    // Admin creates exam
    // Header: X-Admin-Id = adminId
    @PostMapping
    public ResponseEntity<Exam> createExam(
            @Valid @RequestBody ExamRequest request,
            @RequestHeader("X-Admin-Id") Long adminId) {
        return ResponseEntity.ok(examService.createExam(request, adminId));
    }

    // Get all active exams (students see this)
    @GetMapping("/active")
    public ResponseEntity<List<Exam>> getActiveExams() {
        return ResponseEntity.ok(examService.getAllActiveExams());
    }

    // Get all exams (admin)
    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    // Get exam by ID
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }

    // Get questions of exam
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestions(
            @PathVariable Long id) {
        return ResponseEntity.ok(examService.getQuestionsByExam(id));
    }

    // Get exams by admin
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<Exam>> getExamsByAdmin(
            @PathVariable Long adminId) {
        return ResponseEntity.ok(examService.getExamsByAdmin(adminId));
    }

    // Deactivate exam
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Exam> deactivateExam(@PathVariable Long id) {
        return ResponseEntity.ok(examService.deactivateExam(id));
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Exam service is running!");
    }
}