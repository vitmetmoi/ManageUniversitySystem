package com.example.myapp.frame;

import com.example.myapp.courses.Course;
import com.example.myapp.courses.CourseRepository;
import com.example.myapp.frame.dto.FrameAssignmentRequest;
import com.example.myapp.frame.dto.FrameAssignmentResponse;
import com.example.myapp.frame.dto.FrameStructureResponse;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import com.example.myapp.knowledgeblocks.KnowledgeBlockRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FrameService {

    private final KnowledgeBlockCourseRepository knowledgeBlockCourseRepository;
    private final KnowledgeBlockMajorRepository knowledgeBlockMajorRepository;
    private final KnowledgeBlockRepository knowledgeBlockRepository;
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;

    // Knowledge Block - Course Operations

    @Transactional(readOnly = true)
    public FrameAssignmentResponse getCourseAssignments(Long knowledgeBlockId) {
        KnowledgeBlock knowledgeBlock = knowledgeBlockRepository.findById(knowledgeBlockId)
                .orElseThrow(() -> new RuntimeException("Knowledge block not found"));

        List<KnowledgeBlockCourse> assignedRelations = knowledgeBlockCourseRepository
                .findByKnowledgeBlockId(knowledgeBlockId);
        List<Course> unassignedCourses = knowledgeBlockCourseRepository
                .findUnassignedCourses(knowledgeBlockId);

        List<FrameAssignmentResponse.AssignedItem> assignedItems = assignedRelations.stream()
                .map(rel -> FrameAssignmentResponse.AssignedItem.builder()
                        .id(rel.getCourse().getId())
                        .code(rel.getCourse().getCode())
                        .name(rel.getCourse().getName())
                        .description(rel.getCourse().getDescription())
                        .build())
                .collect(Collectors.toList());

        List<FrameAssignmentResponse.UnassignedItem> unassignedItems = unassignedCourses.stream()
                .map(course -> FrameAssignmentResponse.UnassignedItem.builder()
                        .id(course.getId())
                        .code(course.getCode())
                        .name(course.getName())
                        .description(course.getDescription())
                        .build())
                .collect(Collectors.toList());

        return FrameAssignmentResponse.builder()
                .knowledgeBlockId(knowledgeBlock.getId())
                .knowledgeBlockCode(knowledgeBlock.getCode())
                .knowledgeBlockName(knowledgeBlock.getName())
                .assignedItems(assignedItems)
                .unassignedItems(unassignedItems)
                .build();
    }

    public void assignCourseToKnowledgeBlock(FrameAssignmentRequest request) {
        KnowledgeBlock knowledgeBlock = knowledgeBlockRepository.findById(request.getKnowledgeBlockId())
                .orElseThrow(() -> new RuntimeException("Knowledge block not found"));
        Course course = courseRepository.findById(request.getTargetId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (knowledgeBlockCourseRepository.existsByKnowledgeBlockAndCourse(knowledgeBlock, course)) {
            throw new RuntimeException("Course is already assigned to this knowledge block");
        }

        KnowledgeBlockCourse relationship = KnowledgeBlockCourse.builder()
                .knowledgeBlock(knowledgeBlock)
                .course(course)
                .build();

        knowledgeBlockCourseRepository.save(relationship);
        log.info("Assigned course {} to knowledge block {}", course.getCode(), knowledgeBlock.getCode());
    }

    public void unassignCourseFromKnowledgeBlock(Long relationshipId) {
        KnowledgeBlockCourse relationship = knowledgeBlockCourseRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
        
        knowledgeBlockCourseRepository.delete(relationship);
        log.info("Unassigned course {} from knowledge block {}", 
                relationship.getCourse().getCode(), relationship.getKnowledgeBlock().getCode());
    }

    // Knowledge Block - Major Operations

    @Transactional(readOnly = true)
    public FrameAssignmentResponse getMajorAssignments(Long knowledgeBlockId) {
        KnowledgeBlock knowledgeBlock = knowledgeBlockRepository.findById(knowledgeBlockId)
                .orElseThrow(() -> new RuntimeException("Knowledge block not found"));

        List<KnowledgeBlockMajor> assignedRelations = knowledgeBlockMajorRepository
                .findByKnowledgeBlockId(knowledgeBlockId);
        List<Major> unassignedMajors = knowledgeBlockMajorRepository
                .findUnassignedMajors(knowledgeBlockId);

        List<FrameAssignmentResponse.AssignedItem> assignedItems = assignedRelations.stream()
                .map(rel -> FrameAssignmentResponse.AssignedItem.builder()
                        .id(rel.getMajor().getId())
                        .code(rel.getMajor().getCode())
                        .name(rel.getMajor().getName())
                        .description(rel.getMajor().getDescription())
                        .build())
                .collect(Collectors.toList());

        List<FrameAssignmentResponse.UnassignedItem> unassignedItems = unassignedMajors.stream()
                .map(major -> FrameAssignmentResponse.UnassignedItem.builder()
                        .id(major.getId())
                        .code(major.getCode())
                        .name(major.getName())
                        .description(major.getDescription())
                        .build())
                .collect(Collectors.toList());

        return FrameAssignmentResponse.builder()
                .knowledgeBlockId(knowledgeBlock.getId())
                .knowledgeBlockCode(knowledgeBlock.getCode())
                .knowledgeBlockName(knowledgeBlock.getName())
                .assignedItems(assignedItems)
                .unassignedItems(unassignedItems)
                .build();
    }

    public void assignMajorToKnowledgeBlock(FrameAssignmentRequest request) {
        KnowledgeBlock knowledgeBlock = knowledgeBlockRepository.findById(request.getKnowledgeBlockId())
                .orElseThrow(() -> new RuntimeException("Knowledge block not found"));
        Major major = majorRepository.findById(request.getTargetId())
                .orElseThrow(() -> new RuntimeException("Major not found"));

        if (knowledgeBlockMajorRepository.existsByKnowledgeBlockAndMajor(knowledgeBlock, major)) {
            throw new RuntimeException("Major is already assigned to this knowledge block");
        }

        KnowledgeBlockMajor relationship = KnowledgeBlockMajor.builder()
                .knowledgeBlock(knowledgeBlock)
                .major(major)
                .build();

        knowledgeBlockMajorRepository.save(relationship);
        log.info("Assigned major {} to knowledge block {}", major.getCode(), knowledgeBlock.getCode());
    }

    public void unassignMajorFromKnowledgeBlock(Long relationshipId) {
        KnowledgeBlockMajor relationship = knowledgeBlockMajorRepository.findById(relationshipId)
                .orElseThrow(() -> new RuntimeException("Relationship not found"));
        
        knowledgeBlockMajorRepository.delete(relationship);
        log.info("Unassigned major {} from knowledge block {}", 
                relationship.getMajor().getCode(), relationship.getKnowledgeBlock().getCode());
    }

    // Frame Structure Operations

    @Transactional(readOnly = true)
    public List<FrameStructureResponse> getFrameStructure() {
        List<Major> majors = majorRepository.findAll();
        
        return majors.stream()
                .map(major -> {
                    List<KnowledgeBlockMajor> kbMajorRelations = knowledgeBlockMajorRepository
                            .findByMajorId(major.getId());
                    
                    List<FrameStructureResponse.KnowledgeBlockInfo> knowledgeBlocks = kbMajorRelations.stream()
                            .map(rel -> {
                                KnowledgeBlock kb = rel.getKnowledgeBlock();
                                List<KnowledgeBlockCourse> kbCourseRelations = knowledgeBlockCourseRepository
                                        .findByKnowledgeBlockId(kb.getId());
                                
                                List<FrameStructureResponse.KnowledgeBlockInfo.CourseInfo> courses = 
                                        kbCourseRelations.stream()
                                                .map(courseRel -> {
                                                    Course course = courseRel.getCourse();
                                                    return FrameStructureResponse.KnowledgeBlockInfo.CourseInfo.builder()
                                                            .id(course.getId())
                                                            .code(course.getCode())
                                                            .name(course.getName())
                                                            .credits(course.getCredits())
                                                            .build();
                                                })
                                                .collect(Collectors.toList());
                                
                                return FrameStructureResponse.KnowledgeBlockInfo.builder()
                                        .id(kb.getId())
                                        .code(kb.getCode())
                                        .name(kb.getName())
                                        .sequenceOrder(kb.getSequenceOrder())
                                        .courses(courses)
                                        .build();
                            })
                            .collect(Collectors.toList());
                    
                    return FrameStructureResponse.builder()
                            .majorId(major.getId())
                            .majorCode(major.getCode())
                            .majorName(major.getName())
                            .knowledgeBlocks(knowledgeBlocks)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FrameStructureResponse getFrameStructureByMajor(Long majorId) {
        Major major = majorRepository.findById(majorId)
                .orElseThrow(() -> new RuntimeException("Major not found"));

        List<KnowledgeBlockMajor> kbMajorRelations = knowledgeBlockMajorRepository
                .findByMajorId(majorId);
        
        List<FrameStructureResponse.KnowledgeBlockInfo> knowledgeBlocks = kbMajorRelations.stream()
                .map(rel -> {
                    KnowledgeBlock kb = rel.getKnowledgeBlock();
                    List<KnowledgeBlockCourse> kbCourseRelations = knowledgeBlockCourseRepository
                            .findByKnowledgeBlockId(kb.getId());
                    
                    List<FrameStructureResponse.KnowledgeBlockInfo.CourseInfo> courses = 
                            kbCourseRelations.stream()
                                    .map(courseRel -> {
                                        Course course = courseRel.getCourse();
                                        return FrameStructureResponse.KnowledgeBlockInfo.CourseInfo.builder()
                                                .id(course.getId())
                                                .code(course.getCode())
                                                .name(course.getName())
                                                .credits(course.getCredits())
                                                .build();
                                    })
                                    .collect(Collectors.toList());
                    
                    return FrameStructureResponse.KnowledgeBlockInfo.builder()
                            .id(kb.getId())
                            .code(kb.getCode())
                            .name(kb.getName())
                            .sequenceOrder(kb.getSequenceOrder())
                            .courses(courses)
                            .build();
                })
                .collect(Collectors.toList());
        
        return FrameStructureResponse.builder()
                .majorId(major.getId())
                .majorCode(major.getCode())
                .majorName(major.getName())
                .knowledgeBlocks(knowledgeBlocks)
                .build();
    }
}

