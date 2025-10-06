package com.example.myapp.major;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findByFacultyId(Long facultyId);
}


