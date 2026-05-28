package com.example.result_service.service;

import com.example.result_service.dto.AnswerDto;
import com.example.result_service.dto.ResultRequest;
import com.example.result_service.dto.ResultResponse;
import com.example.result_service.entity.Result;
import com.example.result_service.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    // IST timezone helper
    // Same as your formatIST() from MERN portal
    private String formatIST(LocalDateTime dateTime) {
        ZonedDateTime istTime = dateTime
                .atZone(ZoneId.of("Asia/Kolkata"));
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy hh:mm:ss a z");
        return istTime.format(formatter);
    }

    // Calculate and save result
    public ResultResponse evaluateResult(ResultRequest request) {

        // Check if already evaluated
        if (resultRepository
                .findByAttemptId(request.getAttemptId())
                .isPresent()) {
            throw new RuntimeException(
                    "Result already evaluated for this attempt");
        }

        int correctAnswers = 0;
        int wrongAnswers = 0;
        int totalQuestions = 0;

        if (request.getAnswers() != null) {
            totalQuestions = request.getAnswers().size();

            for (AnswerDto answer : request.getAnswers()) {
                if (answer.getSelectedAnswer() != null
                        && answer.getSelectedAnswer()
                        .equalsIgnoreCase(
                                answer.getCorrectAnswer())) {
                    correctAnswers++;
                } else {
                    wrongAnswers++;
                }
            }
        }

        int totalScore = correctAnswers
                * request.getMarksPerQuestion();
        int maxScore = totalQuestions
                * request.getMarksPerQuestion();

        double percentage = maxScore > 0
                ? ((double) totalScore / maxScore) * 100
                : 0.0;

        // Pass if >= 40%
        String resultStatus = percentage >= 40
                ? "PASS" : "FAIL";

        LocalDateTime now = LocalDateTime.now(
                ZoneId.of("Asia/Kolkata"));

        Result result = new Result();
        result.setStudentId(request.getStudentId());
        result.setExamId(request.getExamId());
        result.setAttemptId(request.getAttemptId());
        result.setStudentName(request.getStudentName());
        result.setExamTitle(request.getExamTitle());
        result.setTotalScore(totalScore);
        result.setMaxScore(maxScore);
        result.setCorrectAnswers(correctAnswers);
        result.setWrongAnswers(wrongAnswers);
        result.setTotalQuestions(totalQuestions);
        result.setPercentage(
                Math.round(percentage * 100.0) / 100.0);
        result.setResult(resultStatus);
        result.setEvaluatedAt(now);
        result.setEvaluatedAtIST(formatIST(now));

        Result saved = resultRepository.save(result);
        return mapToResponse(saved);
    }

    // Get result by attempt ID
    public ResultResponse getResultByAttemptId(String attemptId) {
        Result result = resultRepository
                .findByAttemptId(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Result not found for attempt: "
                                        + attemptId));
        return mapToResponse(result);
    }

    // Get all results by student
    public List<ResultResponse> getResultsByStudent(
            Long studentId) {
        return resultRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all results for an exam
    public List<ResultResponse> getResultsByExam(Long examId) {
        return resultRepository
                .findByExamId(examId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get result by student and exam
    public ResultResponse getResultByStudentAndExam(
            Long studentId, Long examId) {
        Result result = resultRepository
                .findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() ->
                        new RuntimeException("Result not found"));
        return mapToResponse(result);
    }

    // Get all results
    public List<ResultResponse> getAllResults() {
        return resultRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Map entity to response
    private ResultResponse mapToResponse(Result result) {
        ResultResponse response = new ResultResponse();
        response.setId(result.getId());
        response.setStudentId(result.getStudentId());
        response.setExamId(result.getExamId());
        response.setStudentName(result.getStudentName());
        response.setExamTitle(result.getExamTitle());
        response.setTotalScore(result.getTotalScore());
        response.setMaxScore(result.getMaxScore());
        response.setCorrectAnswers(result.getCorrectAnswers());
        response.setWrongAnswers(result.getWrongAnswers());
        response.setTotalQuestions(result.getTotalQuestions());
        response.setPercentage(result.getPercentage());
        response.setResult(result.getResult());
        response.setEvaluatedAtIST(result.getEvaluatedAtIST());
        return response;
    }
}