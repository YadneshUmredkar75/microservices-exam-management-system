package com.example.user_service.controller;

import com.example.user_service.dto.StudentRequest;
import com.example.user_service.entity.Student;
import com.example.user_service.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> create(
            @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(
                studentService.createStudent(request));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/auth/{authUserId}")
    public ResponseEntity<Student> getByAuthUserId(
            @PathVariable Long authUserId) {
        return ResponseEntity.ok(
                studentService.getStudentByAuthUserId(authUserId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Student>> getByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(
                studentService.getStudentsByDepartment(departmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(
                studentService.updateStudent(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Student> deactivate(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                studentService.deactivateStudent(id));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User service is running!");
    }
}