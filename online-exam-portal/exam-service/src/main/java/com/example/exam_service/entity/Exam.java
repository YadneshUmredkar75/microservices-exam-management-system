package com.example.exam_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exam")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Integer marksPerQuestion;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    // Admin who created this exam
    @Column(nullable = false)
    private Long createdByAdminId;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "exam",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();
}
