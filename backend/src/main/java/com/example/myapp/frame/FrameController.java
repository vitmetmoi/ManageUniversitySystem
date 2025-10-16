package com.example.myapp.frame;

import com.example.myapp.frame.dto.FrameAssignmentRequest;
import com.example.myapp.frame.dto.FrameAssignmentResponse;
import com.example.myapp.frame.dto.FrameStructureResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frame")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FrameController {

    private final FrameService frameService;

    // Knowledge Block - Course Endpoints

    @GetMapping("/course-assignments/{knowledgeBlockId}")
    public ResponseEntity<FrameAssignmentResponse> getCourseAssignments(
            @PathVariable Long knowledgeBlockId) {
        try {
            FrameAssignmentResponse response = frameService.getCourseAssignments(knowledgeBlockId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting course assignments for knowledge block {}", knowledgeBlockId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/assign-course")
    public ResponseEntity<String> assignCourseToKnowledgeBlock(
            @Valid @RequestBody FrameAssignmentRequest request) {
        try {
            frameService.assignCourseToKnowledgeBlock(request);
            return ResponseEntity.ok("Course assigned successfully");
        } catch (Exception e) {
            log.error("Error assigning course to knowledge block", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/assign-course")
    public ResponseEntity<String> updateCourseAssignment(
            @Valid @RequestBody FrameAssignmentRequest request) {
        try {
            frameService.assignCourseToKnowledgeBlock(request);
            return ResponseEntity.ok("Course assignment updated successfully");
        } catch (Exception e) {
            log.error("Error updating course assignment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/unassign-course/{relationshipId}")
    public ResponseEntity<String> unassignCourseFromKnowledgeBlock(
            @PathVariable Long relationshipId) {
        try {
            frameService.unassignCourseFromKnowledgeBlock(relationshipId);
            return ResponseEntity.ok("Course unassigned successfully");
        } catch (Exception e) {
            log.error("Error unassigning course from knowledge block", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Knowledge Block - Major Endpoints

    @GetMapping("/major-assignments/{knowledgeBlockId}")
    public ResponseEntity<FrameAssignmentResponse> getMajorAssignments(
            @PathVariable Long knowledgeBlockId) {
        try {
            FrameAssignmentResponse response = frameService.getMajorAssignments(knowledgeBlockId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting major assignments for knowledge block {}", knowledgeBlockId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/assign-major")
    public ResponseEntity<String> assignMajorToKnowledgeBlock(
            @Valid @RequestBody FrameAssignmentRequest request) {
        try {
            frameService.assignMajorToKnowledgeBlock(request);
            return ResponseEntity.ok("Major assigned successfully");
        } catch (Exception e) {
            log.error("Error assigning major to knowledge block", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/assign-major")
    public ResponseEntity<String> updateMajorAssignment(
            @Valid @RequestBody FrameAssignmentRequest request) {
        try {
            frameService.assignMajorToKnowledgeBlock(request);
            return ResponseEntity.ok("Major assignment updated successfully");
        } catch (Exception e) {
            log.error("Error updating major assignment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/unassign-major/{relationshipId}")
    public ResponseEntity<String> unassignMajorFromKnowledgeBlock(
            @PathVariable Long relationshipId) {
        try {
            frameService.unassignMajorFromKnowledgeBlock(relationshipId);
            return ResponseEntity.ok("Major unassigned successfully");
        } catch (Exception e) {
            log.error("Error unassigning major from knowledge block", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Frame Structure Endpoints

    @GetMapping("/structure")
    public ResponseEntity<List<FrameStructureResponse>> getFrameStructure() {
        try {
            List<FrameStructureResponse> response = frameService.getFrameStructure();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting frame structure", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/structure/major/{majorId}")
    public ResponseEntity<FrameStructureResponse> getFrameStructureByMajor(
            @PathVariable Long majorId) {
        try {
            FrameStructureResponse response = frameService.getFrameStructureByMajor(majorId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting frame structure for major {}", majorId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Utility Endpoints

    @GetMapping("/knowledge-blocks")
    public ResponseEntity<List<Object>> getAllKnowledgeBlocks() {
        try {
            // This endpoint can be used to get all knowledge blocks for selection
            // Implementation would depend on existing knowledge block service
            return ResponseEntity.ok(List.of());
        } catch (Exception e) {
            log.error("Error getting knowledge blocks", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
