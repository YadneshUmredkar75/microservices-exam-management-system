package com.example.result_service.controller;

import com.example.result_service.dto.ResultRequest;
import com.example.result_service.dto.ResultResponse;
import com.example.result_service.service.ResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    // Evaluate result after exam submission
    @PostMapping("/evaluate")
    public ResponseEntity<ResultResponse> evaluate(
            @Valid @RequestBody ResultRequest request) {
        return ResponseEntity.ok(
                resultService.evaluateResult(request));
    }

    // Get result by attempt ID
    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<ResultResponse> getByAttempt(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                resultService.getResultByAttemptId(attemptId));
    }

    // Get all results by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ResultResponse>> getByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(
                resultService.getResultsByStudent(studentId));
    }

    // Get all results for exam (admin sees this)
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ResultResponse>> getByExam(
            @PathVariable Long examId) {
        return ResponseEntity.ok(
                resultService.getResultsByExam(examId));
    }

    // Get result for specific student in specific exam
    @GetMapping("/student/{studentId}/exam/{examId}")
    public ResponseEntity<ResultResponse> getByStudentAndExam(
            @PathVariable Long studentId,
            @PathVariable Long examId) {
        return ResponseEntity.ok(
                resultService.getResultByStudentAndExam(
                        studentId, examId));
    }

    // Get all results (superadmin)
    @GetMapping
    public ResponseEntity<List<ResultResponse>> getAll() {
        return ResponseEntity.ok(
                resultService.getAllResults());
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Result service is running!");
    }
}