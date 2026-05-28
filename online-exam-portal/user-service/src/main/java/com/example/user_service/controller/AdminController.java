package com.example.user_service.controller;

import com.example.user_service.dto.AdminRequest;
import com.example.user_service.entity.Admin;
import com.example.user_service.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> create(
            @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(adminService.createAdmin(request));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAll() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @GetMapping("/auth/{authUserId}")
    public ResponseEntity<Admin> getByAuthUserId(
            @PathVariable Long authUserId) {
        return ResponseEntity.ok(
                adminService.getAdminByAuthUserId(authUserId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Admin>> getByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(
                adminService.getAdminsByDepartment(departmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(
                adminService.updateAdmin(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Admin> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deactivateAdmin(id));
    }
}