package com.example.myapp.faculty;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.faculty.dto.FacultyRequest;
import com.example.myapp.faculty.dto.FacultyResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public List<FacultyResponse> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/{id}")
    public FacultyResponse getById(@PathVariable Long id) {
        return facultyService.getById(id);
    }

    @PostMapping
    public FacultyResponse create(@Valid @RequestBody FacultyRequest request) {
        System.out.println("create faculty: " + request);
        return facultyService.create(request);
    }

    @PutMapping("/{id}")
    public FacultyResponse update(@PathVariable Long id, @Valid @RequestBody FacultyRequest request) {
        return facultyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


