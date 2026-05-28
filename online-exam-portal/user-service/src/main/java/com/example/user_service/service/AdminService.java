package com.example.user_service.service;

import com.example.user_service.dto.AdminRequest;
import com.example.user_service.entity.Admin;
import com.example.user_service.entity.Department;
import com.example.user_service.repository.AdminRepository;
import com.example.user_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final DepartmentRepository departmentRepository;

    public Admin createAdmin(AdminRequest request) {
        Admin admin = new Admin();
        admin.setAuthUserId(request.getAuthUserId());
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setIsActive(true);

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository
                    .findById(request.getDepartmentId())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));
            admin.setDepartment(dept);
        }

        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findByIsActiveTrue();
    }

    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found: " + id));
    }

    public Admin getAdminByAuthUserId(Long authUserId) {
        return adminRepository.findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));
    }

    public List<Admin> getAdminsByDepartment(Long departmentId) {
        return adminRepository.findByDepartmentId(departmentId);
    }

    public Admin updateAdmin(Long id, AdminRequest request) {
        Admin admin = getAdminById(id);
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());

        if (request.getDepartmentId() != null) {
            Department dept = departmentRepository
                    .findById(request.getDepartmentId())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));
            admin.setDepartment(dept);
        }

        return adminRepository.save(admin);
    }

    public Admin deactivateAdmin(Long id) {
        Admin admin = getAdminById(id);
        admin.setIsActive(false);
        return adminRepository.save(admin);
    }
}