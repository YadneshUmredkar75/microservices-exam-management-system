package com.example.user_service.repository;

import com.example.user_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository
        extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByAuthUserId(Long authUserId);
    List<Admin> findByDepartmentId(Long departmentId);
    List<Admin> findByIsActiveTrue();
}