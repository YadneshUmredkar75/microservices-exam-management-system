package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links to auth-service user id
    @Column(unique = true, nullable = false)
    private Long authUserId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String rollNumber;

    @Column
    private String semester;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private Boolean isActive = true;
}