package com.example.exam_service.service;

import com.example.exam_service.dto.ExamRequest;
import com.example.exam_service.entity.Exam;
import com.example.exam_service.entity.Question;
import com.example.exam_service.repository.ExamRepository;
import com.example.exam_service.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    // Create exam with questions
    @Transactional
    public Exam createExam(ExamRequest request, Long adminId) {

        // Build exam
        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .subject(request.getSubject())
                .marksPerQuestion(request.getMarksPerQuestion())
                .durationMinutes(request.getDurationMinutes())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createdByAdminId(adminId)
                .isActive(true)
                .build();

        Exam savedExam = examRepository.save(exam);

        // Add questions if provided
        if (request.getQuestions() != null
                && !request.getQuestions().isEmpty()) {

            List<Question> questions = request.getQuestions()
                    .stream()
                    .map(q -> Question.builder()
                            .questionText(q.getQuestionText())
                            .optionA(q.getOptionA())
                            .optionB(q.getOptionB())
                            .optionC(q.getOptionC())
                            .optionD(q.getOptionD())
                            .correctAnswer(q.getCorrectAnswer())
                            .exam(savedExam)
                            .build())
                    .collect(Collectors.toList());

            questionRepository.saveAll(questions);
        }

        return savedExam;
    }

    // Get all active exams (for students)
    public List<Exam> getAllActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    // Get exam by ID
    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found with id: " + id));
    }

    // Get exams by admin
    public List<Exam> getExamsByAdmin(Long adminId) {
        return examRepository.findByCreatedByAdminId(adminId);
    }

    // Get questions of an exam
    public List<Question> getQuestionsByExam(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    // Deactivate exam
    public Exam deactivateExam(Long examId) {
        Exam exam = getExamById(examId);
        exam.setIsActive(false);
        return examRepository.save(exam);
    }

    // Get all exams (for admin)
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
}