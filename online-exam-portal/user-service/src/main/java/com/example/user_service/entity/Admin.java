package com.example.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This links to auth-service user id
    @Column(unique = true, nullable = false)
    private Long authUserId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false)
    private Boolean isActive = true;
}