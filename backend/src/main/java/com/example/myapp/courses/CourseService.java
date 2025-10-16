package com.example.myapp.courses;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.courses.dto.CourseRequest;
import com.example.myapp.courses.dto.CourseResponse;
import com.example.myapp.courses.mapper.CourseMapper;
import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.FacultyRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;

    public Page<CourseResponse> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(CourseMapper::toResponse);
    }

    public Page<CourseResponse> findByMajor(Long majorId, Pageable pageable) {
        return courseRepository.findAllByMajor_Id(majorId, pageable).map(CourseMapper::toResponse);
    }

    public Page<CourseResponse> findByFaculty(Long facultyId, Pageable pageable) {
        return courseRepository.findAllByFaculty_Id(facultyId, pageable).map(CourseMapper::toResponse);
    }

    public CourseResponse getById(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        return CourseMapper.toResponse(c);
    }

    public CourseResponse create(CourseRequest req) {
        
        courseRepository.findByCode(req.getCode()).ifPresent(x -> {
            throw new DataIntegrityViolationException("Course code already exists");
        });
        Course c = CourseMapper.toEntity(req);
        if (req.getFacultyId() != null) {
            Faculty f = facultyRepository.findById(req.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + req.getFacultyId()));
            c.setFaculty(f);
        }
       
        return CourseMapper.toResponse(courseRepository.save(c));
    }

    public CourseResponse update(Long id, CourseRequest req) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        courseRepository.findByCode(req.getCode()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new DataIntegrityViolationException("Course code already exists");
            }
        });
        CourseMapper.updateEntity(c, req);
        if (req.getFacultyId() != null) {
            Faculty f = facultyRepository.findById(req.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + req.getFacultyId()));
            c.setFaculty(f);
        } else {
            c.setFaculty(null);
        }
       
        return CourseMapper.toResponse(courseRepository.save(c));
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id " + id);
        }
        courseRepository.deleteById(id);
    }
}


