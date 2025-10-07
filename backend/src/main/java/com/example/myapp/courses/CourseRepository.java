package com.example.myapp.courses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
    Page<Course> findAllByMajor_Id(Long majorId, Pageable pageable);
    Page<Course> findAllByFaculty_Id(Long facultyId, Pageable pageable);
    List<Course> findAllByMajor_Id(Long majorId);
}


