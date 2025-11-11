package com.example.myapp.outline;

import com.example.myapp.outline.dto.OutlineRequest;
import com.example.myapp.outline.dto.OutlineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/outlines")
@RequiredArgsConstructor
public class OutlineController {
    private final OutlineService outlineService;

    @GetMapping
    public List<OutlineResponse> getAll() {
        return outlineService.getAll();
    }

    @GetMapping("/{id}")
    public OutlineResponse getById(@PathVariable Long id) {
        return outlineService.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OutlineResponse create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("courseId") Long courseId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) String status) {
        OutlineRequest request = new OutlineRequest();
        request.setFilePath(file);
        request.setCourse_id(courseId);
        request.setDescription(description);
        request.setStatus(status != null ? status : "ACTIVE");
        return outlineService.create(request);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OutlineResponse update(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "status", required = false) String status) {
        OutlineRequest request = new OutlineRequest();
        request.setFilePath(file);
        request.setCourse_id(courseId);
        request.setDescription(description);
        request.setStatus(status);
        return outlineService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        outlineService.delete(id);
    }
}