package com.example.myapp.major;

import com.example.myapp.major.dto.MajorRequest;
import com.example.myapp.major.dto.MajorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @GetMapping
    public List<MajorResponse> getAll() {
        return majorService.getAll();
    }

    @GetMapping("/{id}")
    public MajorResponse getById(@PathVariable Long id) {
        return majorService.getById(id);
    }

    @GetMapping("/by-faculty/{facultyId}")
    public List<MajorResponse> getByFaculty(@PathVariable Long facultyId) {
        return majorService.findByFaculty(facultyId);
    }

    @PostMapping
    public MajorResponse create(@Valid @RequestBody MajorRequest request) {
        return majorService.create(request);
    }

    @PutMapping("/{id}")
    public MajorResponse update(@PathVariable Long id, @Valid @RequestBody MajorRequest request) {
        return majorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        majorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


