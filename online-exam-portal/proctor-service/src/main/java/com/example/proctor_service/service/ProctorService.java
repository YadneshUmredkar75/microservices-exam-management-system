package com.example.proctor_service.service;

import com.example.proctor_service.dto.FaceEventRequest;
import com.example.proctor_service.dto.SessionRequest;
import com.example.proctor_service.model.FaceEvent;
import com.example.proctor_service.model.ProctorSession;
import com.example.proctor_service.repository.FaceEventRepository;
import com.example.proctor_service.repository.ProctorSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProctorService {

    private final FaceEventRepository      faceEventRepository;
    private final ProctorSessionRepository sessionRepository;

    // IST formatter — same as your MERN formatIST()
    private String formatIST(LocalDateTime dt) {
        ZonedDateTime ist = dt.atZone(
                ZoneId.of("Asia/Kolkata"));
        return ist.format(DateTimeFormatter
                .ofPattern("dd-MM-yyyy hh:mm:ss a z"));
    }

    // ── Session ──────────────────────────────────────────

    // Start proctoring session when student starts exam
    public ProctorSession startSession(SessionRequest req) {

        // Return existing session if already exists
        sessionRepository
                .findByStudentIdAndExamId(
                        req.getStudentId(), req.getExamId())
                .ifPresent(s -> {
                    throw new RuntimeException(
                            "Session already exists for this student");
                });

        ProctorSession session = new ProctorSession();
        session.setAttemptId(req.getAttemptId());
        session.setStudentId(req.getStudentId());
        session.setExamId(req.getExamId());
        session.setStudentName(req.getStudentName());
        session.setStatus("ACTIVE");
        session.setStartedAt(
                LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        session.setFaceAbsenceCount(0);
        session.setMultipleFaceCount(0);
        session.setTabSwitchCount(0);
        session.setTotalWarnings(0);
        session.setIsTerminated(false);
        session.setWarningLimit(5);

        return sessionRepository.save(session);
    }

    // End session when student submits exam
    public ProctorSession endSession(String attemptId) {
        ProctorSession session = sessionRepository
                .findByAttemptId(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found"));

        session.setStatus("COMPLETED");
        session.setEndedAt(
                LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        return sessionRepository.save(session);
    }

    // Get session by attempt
    public ProctorSession getSession(String attemptId) {
        return sessionRepository
                .findByAttemptId(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Session not found"));
    }

    // Get all sessions for exam (admin view)
    public List<ProctorSession> getSessionsByExam(Long examId) {
        return sessionRepository.findByExamId(examId);
    }

    // Get terminated sessions
    public List<ProctorSession> getTerminatedSessions() {
        return sessionRepository.findByIsTerminatedTrue();
    }

    // ── Face Events ───────────────────────────────────────

    // Record face event from frontend canvas detection
    // Same as your canvas-based face presence detection in MERN
    public FaceEvent recordEvent(FaceEventRequest req) {

        LocalDateTime now = LocalDateTime.now(
                ZoneId.of("Asia/Kolkata"));

        FaceEvent event = new FaceEvent();
        event.setAttemptId(req.getAttemptId());
        event.setStudentId(req.getStudentId());
        event.setExamId(req.getExamId());
        event.setEventType(req.getEventType());
        event.setMessage(req.getMessage());
        event.setEventTime(now);
        event.setEventTimeIST(formatIST(now));

        // Update session warning count
        ProctorSession session = sessionRepository
                .findByAttemptId(req.getAttemptId())
                .orElse(null);

        if (session != null && !session.getIsTerminated()) {

            switch (req.getEventType()) {
                case "FACE_MISSING" -> {
                    session.setFaceAbsenceCount(
                            session.getFaceAbsenceCount() + 1);
                    session.setTotalWarnings(
                            session.getTotalWarnings() + 1);
                }
                case "MULTIPLE_FACES" -> {
                    session.setMultipleFaceCount(
                            session.getMultipleFaceCount() + 1);
                    session.setTotalWarnings(
                            session.getTotalWarnings() + 1);
                }
                case "TAB_SWITCH" -> {
                    session.setTabSwitchCount(
                            session.getTabSwitchCount() + 1);
                    session.setTotalWarnings(
                            session.getTotalWarnings() + 1);
                }
            }

            event.setWarningCount(session.getTotalWarnings());

            // Auto-terminate if warnings exceed limit
            if (session.getTotalWarnings()
                    >= session.getWarningLimit()) {
                session.setIsTerminated(true);
                session.setStatus("TERMINATED");
                session.setTerminationReason(
                        "Exceeded maximum warnings: "
                                + session.getTotalWarnings());
                session.setEndedAt(now);
            }

            sessionRepository.save(session);
        }

        return faceEventRepository.save(event);
    }

    // Get all events for an attempt
    public List<FaceEvent> getEventsByAttempt(String attemptId) {
        return faceEventRepository.findByAttemptId(attemptId);
    }

    // Get warning count for attempt
    public long getWarningCount(String attemptId) {
        return faceEventRepository
                .countByAttemptIdAndEventType(
                        attemptId, "FACE_MISSING")
                + faceEventRepository
                .countByAttemptIdAndEventType(
                        attemptId, "MULTIPLE_FACES")
                + faceEventRepository
                .countByAttemptIdAndEventType(
                        attemptId, "TAB_SWITCH");
    }

    // Check if session is terminated
    public boolean isTerminated(String attemptId) {
        return sessionRepository
                .findByAttemptId(attemptId)
                .map(ProctorSession::getIsTerminated)
                .orElse(false);
    }
}