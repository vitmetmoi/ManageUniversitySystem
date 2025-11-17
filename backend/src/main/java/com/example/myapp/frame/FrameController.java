package com.example.myapp.frame;

import com.example.myapp.frame.dto.FrameAssignmentRequest;
import com.example.myapp.frame.dto.FrameAssignmentResponse;
import com.example.myapp.frame.dto.FrameCreateRequest;
import com.example.myapp.frame.dto.FrameResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frames")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FrameController {

    private final FrameService frameService;

    @GetMapping
    public ResponseEntity<List<FrameResponse>> getAll() {
        return ResponseEntity.ok(frameService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FrameResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(frameService.getById(id));
    }

    @PostMapping
    public ResponseEntity<FrameResponse> create(@Valid @RequestBody FrameCreateRequest req) {
        return ResponseEntity.ok(frameService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FrameResponse> update(@PathVariable Long id, @Valid @RequestBody FrameCreateRequest req) {
        return ResponseEntity.ok(frameService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        frameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/copy")
    public ResponseEntity<FrameResponse> copy(@PathVariable Long id) {
        return ResponseEntity.ok(frameService.copy(id));
    }

    @GetMapping("/course-assignments/{knowledgeBlockId}")
    public ResponseEntity<FrameAssignmentResponse> getCourseAssignments(@PathVariable Long knowledgeBlockId) {
        return ResponseEntity.ok(frameService.getCourseAssignments(knowledgeBlockId));
    }

    @PostMapping("/assign-course")
    public ResponseEntity<Void> assignCourse(@Valid @RequestBody FrameAssignmentRequest request) {
        frameService.assignCourseToKnowledgeBlock(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unassign-course/{relationshipId}")
    public ResponseEntity<Void> unassignCourse(@PathVariable Long relationshipId) {
        frameService.unassignCourseFromKnowledgeBlock(relationshipId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unassign-course")
    public ResponseEntity<Void> unassignCourseByPair(@RequestParam Long knowledgeBlockId, @RequestParam Long courseId) {
        frameService.unassignCourseFromKnowledgeBlock(knowledgeBlockId, courseId);
        return ResponseEntity.ok().build();
    }
}