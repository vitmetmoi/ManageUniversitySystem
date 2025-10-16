package com.example.myapp.employee.mapper;

import com.example.myapp.auth.entity.User;
import com.example.myapp.employee.dto.EmployeeRequest;
import com.example.myapp.employee.dto.EmployeeResponse;

public class EmployeeMapper {
    public static void applyRequest(User user, EmployeeRequest r) {
        user.setEmail(r.getEmail());
        user.setUserName(r.getUserName());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        if (r.getStatus() != null) {
            try { user.setStatus(User.Status.valueOf(r.getStatus())); } catch (IllegalArgumentException ignored) {}
        }
    }

    public static EmployeeResponse toResponse(User u) {
        EmployeeResponse.EmployeeResponseBuilder b = EmployeeResponse.builder()
                .id(u.getId())
                .email(u.getEmail())
                .userName(u.getUserName())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .status(u.getStatus() != null ? u.getStatus().name() : null);
        if (u.getFaculty() != null) {
            b.facultyId(u.getFaculty().getId());
            b.facultyName(u.getFaculty().getName());
        }
        if (u.getMajor() != null) {
            b.majorId(u.getMajor().getId());
            b.majorName(u.getMajor().getName());
        }
        return b.build();
    }
}


