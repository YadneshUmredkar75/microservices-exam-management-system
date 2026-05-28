package com.example.attempt_service.service;

import com.example.attempt_service.dto.AttemptRequest;
import com.example.attempt_service.dto.AttemptResponse;
import com.example.attempt_service.dto.SubmitAnswerRequest;
import com.example.attempt_service.model.ExamAttempt;
import com.example.attempt_service.repository.ExamAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final ExamAttemptRepository attemptRepository;

    // Student starts exam
    public AttemptResponse startAttempt(AttemptRequest request) {

        // Check one attempt per student per exam
        // Same logic as your MERN compound unique index
        boolean alreadyAttempted = attemptRepository
                .findByStudentIdAndExamId(
                        request.getStudentId(),
                        request.getExamId())
                .isPresent();

        if (alreadyAttempted) {
            throw new RuntimeException(
                    "You have already attempted this exam. " +
                            "Only one attempt is allowed.");
        }

        ExamAttempt attempt = new ExamAttempt();
        attempt.setStudentId(request.getStudentId());
        attempt.setExamId(request.getExamId());
        attempt.setAdminId(request.getAdminId());
        attempt.setStudentName(request.getStudentName());
        attempt.setExamTitle(request.getExamTitle());
        attempt.setStatus("IN_PROGRESS");
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setFaceDetected(true);
        attempt.setFaceAbsenceCount(0);

        ExamAttempt saved = attemptRepository.save(attempt);

        return new AttemptResponse(
                "Exam started successfully",
                saved.getId(),
                saved.getStatus()
        );
    }

    // Student submits answers
    public AttemptResponse submitAttempt(
            SubmitAnswerRequest request) {

        ExamAttempt attempt = attemptRepository
                .findById(request.getAttemptId())
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found"));

        if (attempt.getStatus().equals("SUBMITTED")) {
            throw new RuntimeException("Exam already submitted");
        }

        attempt.setAnswers(request.getAnswers());
        attempt.setStatus("SUBMITTED");
        attempt.setSubmittedAt(LocalDateTime.now());

        ExamAttempt saved = attemptRepository.save(attempt);

        return new AttemptResponse(
                "Exam submitted successfully",
                saved.getId(),
                saved.getStatus()
        );
    }

    // Get attempt by ID
    public ExamAttempt getAttemptById(String id) {
        return attemptRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Attempt not found: " + id));
    }

    // Get all attempts by student
    public List<ExamAttempt> getAttemptsByStudent(Long studentId) {
        return attemptRepository.findByStudentId(studentId);
    }

    // Get all attempts for an exam
    public List<ExamAttempt> getAttemptsByExam(Long examId) {
        return attemptRepository.findByExamId(examId);
    }

    // Check if student already attempted
    public boolean hasStudentAttempted(Long studentId, Long examId) {
        return attemptRepository
                .findByStudentIdAndExamId(studentId, examId)
                .isPresent();
    }

    // Update face detection count (called by proctor-service)
    public ExamAttempt updateFaceAbsence(String attemptId) {
        ExamAttempt attempt = getAttemptById(attemptId);
        attempt.setFaceAbsenceCount(
                attempt.getFaceAbsenceCount() + 1);
        attempt.setFaceDetected(false);
        return attemptRepository.save(attempt);
    }

    // Get all submitted attempts (for result-service)
    public List<ExamAttempt> getSubmittedAttempts() {
        return attemptRepository.findByStatus("SUBMITTED");
    }
}