package com.example.myapp.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.auth.entity.User;

public interface EmployeeRepository extends JpaRepository<User, Long> {
    List<User> findByFacultyId(Long facultyId);
    List<User> findByMajorId(Long majorId);
}


