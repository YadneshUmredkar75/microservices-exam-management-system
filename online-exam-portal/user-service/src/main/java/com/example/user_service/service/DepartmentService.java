package com.example.user_service.service;

import com.example.user_service.dto.DepartmentRequest;
import com.example.user_service.entity.Department;
import com.example.user_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department createDepartment(DepartmentRequest request) {
        Department dept = new Department();
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        return departmentRepository.save(dept);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Department not found: " + id));
    }

    public Department updateDepartment(Long id,
                                       DepartmentRequest request) {
        Department dept = getDepartmentById(id);
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        return departmentRepository.save(dept);
    }

    public String deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
        return "Department deleted successfully";
    }
}