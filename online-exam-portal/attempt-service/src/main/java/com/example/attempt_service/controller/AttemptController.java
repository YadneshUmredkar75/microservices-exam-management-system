package com.example.attempt_service.controller;

import com.example.attempt_service.dto.AttemptRequest;
import com.example.attempt_service.dto.AttemptResponse;
import com.example.attempt_service.dto.SubmitAnswerRequest;
import com.example.attempt_service.model.ExamAttempt;
import com.example.attempt_service.service.AttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    // Student starts exam
    @PostMapping("/start")
    public ResponseEntity<AttemptResponse> startAttempt(
            @Valid @RequestBody AttemptRequest request) {
        return ResponseEntity.ok(
                attemptService.startAttempt(request));
    }

    // Student submits exam
    @PostMapping("/submit")
    public ResponseEntity<AttemptResponse> submitAttempt(
            @Valid @RequestBody SubmitAnswerRequest request) {
        return ResponseEntity.ok(
                attemptService.submitAttempt(request));
    }

    // Get attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamAttempt> getById(
            @PathVariable String id) {
        return ResponseEntity.ok(
                attemptService.getAttemptById(id));
    }

    // Get all attempts by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamAttempt>> getByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(
                attemptService.getAttemptsByStudent(studentId));
    }

    // Get all attempts for exam
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ExamAttempt>> getByExam(
            @PathVariable Long examId) {
        return ResponseEntity.ok(
                attemptService.getAttemptsByExam(examId));
    }

    // Check if student already attempted
    @GetMapping("/check/{studentId}/{examId}")
    public ResponseEntity<Boolean> checkAttempt(
            @PathVariable Long studentId,
            @PathVariable Long examId) {
        return ResponseEntity.ok(
                attemptService.hasStudentAttempted(
                        studentId, examId));
    }

    // Update face absence (proctor-service calls this)
    @PatchMapping("/{attemptId}/face-absence")
    public ResponseEntity<ExamAttempt> updateFaceAbsence(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                attemptService.updateFaceAbsence(attemptId));
    }

    // Get all submitted (result-service calls this)
    @GetMapping("/submitted")
    public ResponseEntity<List<ExamAttempt>> getSubmitted() {
        return ResponseEntity.ok(
                attemptService.getSubmittedAttempts());
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Attempt service is running!");
    }
}