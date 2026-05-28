package com.example.proctor_service.controller;

import com.example.proctor_service.dto.FaceEventRequest;
import com.example.proctor_service.dto.SessionRequest;
import com.example.proctor_service.model.FaceEvent;
import com.example.proctor_service.model.ProctorSession;
import com.example.proctor_service.service.ProctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proctor")
@RequiredArgsConstructor
public class ProctorController {

    private final ProctorService proctorService;

    // ── Session endpoints ────────────────────────────────

    // Start session when student starts exam
    @PostMapping("/session/start")
    public ResponseEntity<ProctorSession> startSession(
            @Valid @RequestBody SessionRequest request) {
        return ResponseEntity.ok(
                proctorService.startSession(request));
    }

    // End session when student submits
    @PatchMapping("/session/{attemptId}/end")
    public ResponseEntity<ProctorSession> endSession(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                proctorService.endSession(attemptId));
    }

    // Get session details
    @GetMapping("/session/{attemptId}")
    public ResponseEntity<ProctorSession> getSession(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                proctorService.getSession(attemptId));
    }

    // Get all sessions for exam (admin)
    @GetMapping("/session/exam/{examId}")
    public ResponseEntity<List<ProctorSession>> getByExam(
            @PathVariable Long examId) {
        return ResponseEntity.ok(
                proctorService.getSessionsByExam(examId));
    }

    // Get terminated sessions (admin alert)
    @GetMapping("/session/terminated")
    public ResponseEntity<List<ProctorSession>> getTerminated() {
        return ResponseEntity.ok(
                proctorService.getTerminatedSessions());
    }

    // ── Face Event endpoints ─────────────────────────────

    // Frontend sends face detection events here
    // Called every few seconds from canvas detection
    @PostMapping("/event")
    public ResponseEntity<FaceEvent> recordEvent(
            @Valid @RequestBody FaceEventRequest request) {
        return ResponseEntity.ok(
                proctorService.recordEvent(request));
    }

    // Get all events for attempt (admin review)
    @GetMapping("/event/{attemptId}")
    public ResponseEntity<List<FaceEvent>> getEvents(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                proctorService.getEventsByAttempt(attemptId));
    }

    // Get total warning count
    @GetMapping("/warnings/{attemptId}")
    public ResponseEntity<Long> getWarnings(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                proctorService.getWarningCount(attemptId));
    }

    // Check if session terminated
    // Frontend polls this to know if student was kicked out
    @GetMapping("/terminated/{attemptId}")
    public ResponseEntity<Boolean> isTerminated(
            @PathVariable String attemptId) {
        return ResponseEntity.ok(
                proctorService.isTerminated(attemptId));
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok(
                "Proctor service is running!");
    }
}