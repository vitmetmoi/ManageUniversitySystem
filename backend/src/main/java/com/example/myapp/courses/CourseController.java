package com.example.myapp.courses;

import com.example.myapp.courses.dto.CourseRequest;
import com.example.myapp.courses.dto.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public Page<CourseResponse> getAll(Pageable pageable,
                                       @RequestParam(required = false) Long majorId,
                                       @RequestParam(required = false) Long facultyId) {
        if (majorId != null) return courseService.findByMajor(majorId, pageable);
        if (facultyId != null) return courseService.findByFaculty(facultyId, pageable);
        return courseService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PostMapping
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        System.out.println("hello");
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}


