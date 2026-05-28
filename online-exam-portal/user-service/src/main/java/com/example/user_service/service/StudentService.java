package com.example.user_service.service;

import com.example.user_service.dto.StudentRequest;
import com.example.user_service.entity.Department;
import com.example.user_service.entity.Student;
import com.example.user_service.repository.DepartmentRepository;
import com.example.user_service.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public Student createStudent(StudentRequest request) {
        Student student = new Student();
        student.setAuthUserId(request.getAuthUserId());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setRollNumber(request.getRollNumber());
        student.setSemester(request.getSemester());
        student.setIsActive(true);

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository
                    .findById(request.getDepartmentId())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));
            student.setDepartment(dept);
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findByIsActiveTrue();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found: " + id));
    }

    public Student getStudentByAuthUserId(Long authUserId) {
        return studentRepository.findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    public List<Student> getStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId);
    }

    public Student updateStudent(Long id, StudentRequest request) {
        Student student = getStudentById(id);
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setRollNumber(request.getRollNumber());
        student.setSemester(request.getSemester());

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository
                    .findById(request.getDepartmentId())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));
            student.setDepartment(dept);
        }

        return studentRepository.save(student);
    }

    public Student deactivateStudent(Long id) {
        Student student = getStudentById(id);
        student.setIsActive(false);
        return studentRepository.save(student);
    }
}