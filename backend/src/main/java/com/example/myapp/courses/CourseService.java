package com.example.myapp.courses;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.courses.dto.CourseRequest;
import com.example.myapp.courses.dto.CourseResponse;
import com.example.myapp.courses.mapper.CourseMapper;
import com.example.myapp.frame.KnowledgeBlockCourse;
import com.example.myapp.frame.KnowledgeBlockCourseRepository;
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
    private final KnowledgeBlockCourseRepository knowledgeBlockCourseRepository;
    

    public Page<CourseResponse> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(CourseMapper::toResponse);
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
        if (req.getCourseElectiveId() != null) {
            Course e = courseRepository.findById(req.getCourseElectiveId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseElectiveId()));
            c.setCourseElective(e);
        }
        if (req.getCourseParallelId() != null) {
            Course p = courseRepository.findById(req.getCourseParallelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseParallelId()));
            c.setCourseParallel(p);
        }
        if (req.getCoursePreviousId() != null) {
            Course pv = courseRepository.findById(req.getCoursePreviousId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCoursePreviousId()));
            c.setCoursePrevious(pv);
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
        if (req.getCourseElectiveId() != null) {
            Course e = courseRepository.findById(req.getCourseElectiveId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseElectiveId()));
            c.setCourseElective(e);
        } else {
            c.setCourseElective(null);
        }
        if (req.getCourseParallelId() != null) {
            Course p = courseRepository.findById(req.getCourseParallelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseParallelId()));
            c.setCourseParallel(p);
        } else {
            c.setCourseParallel(null);
        }
        if (req.getCoursePreviousId() != null) {
            Course pv = courseRepository.findById(req.getCoursePreviousId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCoursePreviousId()));
            c.setCoursePrevious(pv);
        } else {
            c.setCoursePrevious(null);
        }
       
        return CourseMapper.toResponse(courseRepository.save(c));
    }

    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id " + id);
        }
        courseRepository.deleteById(id);
    }

    public List<CourseResponse> getByKnowledgeBlock(Long knowledgeBlockId) {
        List<KnowledgeBlockCourse> relations = knowledgeBlockCourseRepository.findByKnowledgeBlockId(knowledgeBlockId);
        return relations.stream()
                .map(KnowledgeBlockCourse::getCourse)
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }
}


