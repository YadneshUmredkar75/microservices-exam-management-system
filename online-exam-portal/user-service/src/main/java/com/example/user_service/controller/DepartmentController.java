package com.example.user_service.controller;

import com.example.user_service.dto.DepartmentRequest;
import com.example.user_service.entity.Department;
import com.example.user_service.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Department> create(
            @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(
                departmentService.createDepartment(request));
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(
                departmentService.deleteDepartment(id));
    }
}