package com.example.myapp.frame;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.courses.Course;
import com.example.myapp.courses.CourseRepository;
import com.example.myapp.curricula.Curriculum;
import com.example.myapp.curricula.CurriculumRepository;
import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.FacultyRepository;
import com.example.myapp.frame.dto.*;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import com.example.myapp.knowledgeblocks.KnowledgeBlockRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FrameService {

        private final FrameRepository frameRepository;
        private final KnowledgeBlockFrameRepository knowledgeBlockFrameRepository;
        private final KnowledgeBlockCourseRepository knowledgeBlockCourseRepository;
        private final KnowledgeBlockRepository knowledgeBlockRepository;
        private final CourseRepository courseRepository;
        private final CurriculumRepository curriculumRepository;
        private final FacultyRepository facultyRepository;
        private final MajorRepository majorRepository;

        @Transactional(readOnly = true)
        public List<FrameResponse> getAll() {
                return frameRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public FrameResponse getById(Long id) {
                Frame frame = frameRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Frame not found with id " + id));
                return toResponse(frame);
        }

        public FrameResponse create(FrameCreateRequest req) {
                Curriculum curriculum = null;
                Faculty faculty = null;
                Major major = null;
                if (req.getCurriculumId() != null) {
                        curriculum = curriculumRepository.findById(req.getCurriculumId()).orElseThrow(
                                        () -> new ResourceNotFoundException(
                                                        "Curriculum not found with id " + req.getCurriculumId()));
                }
                if (req.getFacultyId() != null) {
                        faculty = facultyRepository.findById(req.getFacultyId()).orElseThrow(
                                        () -> new ResourceNotFoundException(
                                                        "Faculty not found with id " + req.getFacultyId()));
                }
                if (req.getMajorId() != null) {
                        major = majorRepository.findById(req.getMajorId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Major not found with id " + req.getMajorId()));
                }

                Frame frame = Frame.builder()
                                .curriculum(curriculum)
                                .faculty(faculty)
                                .major(major)
                                .pricePerCredit(req.getPricePerCredit())
                                .isActive(req.getIsActive() != null ? req.getIsActive() : true)
                                .description(req.getDescription())
                                .build();
                Frame saved = frameRepository.save(frame);

                if (req.getRequirements() != null) {
                        for (KnowledgeBlockRequirementRequest r : req.getRequirements()) {
                                KnowledgeBlock kb = knowledgeBlockRepository.findById(r.getKnowledgeBlockId())
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Knowledge block not found with id "
                                                                                + r.getKnowledgeBlockId()));
                                KnowledgeBlockFrame kbf = KnowledgeBlockFrame.builder()
                                                .frame(saved)
                                                .knowledgeBlock(kb)
                                                .isActive(true)
                                                .elective(Boolean.TRUE.equals(r.getElective()))
                                                .minimumCredits(r.getMinimumCredits())
                                                .build();
                                knowledgeBlockFrameRepository.save(kbf);
                        }
                }

                return toResponse(saved);
        }

        public FrameResponse update(Long id, FrameCreateRequest req) {
                Frame frame = frameRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Frame not found with id " + id));

                frame.setPricePerCredit(req.getPricePerCredit());
                frame.setDescription(req.getDescription());
                frame.setIsActive(req.getIsActive() != null ? req.getIsActive() : frame.getIsActive());

                if (req.getCurriculumId() != null) {
                        Curriculum curriculum = curriculumRepository.findById(req.getCurriculumId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Curriculum not found with id " + req.getCurriculumId()));
                        frame.setCurriculum(curriculum);
                }
                if (req.getFacultyId() != null) {
                        Faculty faculty = facultyRepository.findById(req.getFacultyId())
                                        .orElseThrow(
                                                        () -> new ResourceNotFoundException("Faculty not found with id "
                                                                        + req.getFacultyId()));
                        frame.setFaculty(faculty);
                }
                if (req.getMajorId() != null) {
                        Major major = majorRepository.findById(req.getMajorId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Major not found with id " + req.getMajorId()));
                        frame.setMajor(major);
                }

                Frame saved = frameRepository.save(frame);

                if (req.getRequirements() != null) {
                        knowledgeBlockFrameRepository.deleteByFrame_Id(saved.getId());
                        for (KnowledgeBlockRequirementRequest r : req.getRequirements()) {
                                KnowledgeBlock kb = knowledgeBlockRepository.findById(r.getKnowledgeBlockId())
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Knowledge block not found with id "
                                                                                + r.getKnowledgeBlockId()));
                                KnowledgeBlockFrame kbf = KnowledgeBlockFrame.builder()
                                                .frame(saved)
                                                .knowledgeBlock(kb)
                                                .isActive(true)
                                                .elective(Boolean.TRUE.equals(r.getElective()))
                                                .minimumCredits(r.getMinimumCredits())
                                                .build();
                                knowledgeBlockFrameRepository.save(kbf);
                        }
                }

                return toResponse(saved);
        }

        public void delete(Long id) {
                if (!frameRepository.existsById(id)) {
                        throw new ResourceNotFoundException("Frame not found with id " + id);
                }
                knowledgeBlockFrameRepository.deleteByFrame_Id(id);
                frameRepository.deleteById(id);
        }

        public FrameResponse copy(Long id) {
                Frame original = frameRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Frame not found with id " + id));

                Frame frame = Frame.builder()
                                .curriculum(original.getCurriculum())
                                .faculty(original.getFaculty())
                                .major(original.getMajor())
                                .pricePerCredit(original.getPricePerCredit())
                                .isActive(original.getIsActive())
                                .description(original.getDescription() + " (Copy)")
                                .build();
                Frame saved = frameRepository.save(frame);

                List<KnowledgeBlockFrame> originalRequirements = knowledgeBlockFrameRepository.findByFrame_Id(id);
                for (KnowledgeBlockFrame r : originalRequirements) {
                        KnowledgeBlockFrame kbf = KnowledgeBlockFrame.builder()
                                        .frame(saved)
                                        .knowledgeBlock(r.getKnowledgeBlock())
                                        .isActive(r.getIsActive())
                                        .elective(r.getElective())
                                        .minimumCredits(r.getMinimumCredits())
                                        .build();
                        knowledgeBlockFrameRepository.save(kbf);
                }

                return toResponse(saved);
        }

        @Transactional(readOnly = true)
        public FrameAssignmentResponse getCourseAssignments(Long knowledgeBlockId) {
                KnowledgeBlock knowledgeBlock = knowledgeBlockRepository.findById(knowledgeBlockId)
                                .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found"));

                List<KnowledgeBlockCourse> assignedRelations = knowledgeBlockCourseRepository
                                .findByKnowledgeBlockId(knowledgeBlockId);
                List<Course> unassignedCourses = knowledgeBlockCourseRepository.findUnassignedCourses(knowledgeBlockId);

                List<FrameAssignmentResponse.AssignedItem> assignedItems = assignedRelations.stream()
                                .map(rel -> FrameAssignmentResponse.AssignedItem.builder()
                                                .id(rel.getCourse().getId())
                                                .code(rel.getCourse().getCode())
                                                .name(rel.getCourse().getName())
                                                .credits(rel.getCourse().getCredits())
                                                .description(rel.getCourse().getDescription())
                                                .build())
                                .collect(Collectors.toList());

                List<FrameAssignmentResponse.UnassignedItem> unassignedItems = unassignedCourses.stream()
                                .map(course -> FrameAssignmentResponse.UnassignedItem.builder()
                                                .id(course.getId())
                                                .code(course.getCode())
                                                .name(course.getName())
                                                .credits(course.getCredits())
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
                                .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found"));
                Course course = courseRepository.findById(request.getTargetId())
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

                if (knowledgeBlockCourseRepository.existsByKnowledgeBlockAndCourse(knowledgeBlock, course)) {
                        throw new RuntimeException("Course is already assigned to this knowledge block");
                }

                KnowledgeBlockCourse relationship = KnowledgeBlockCourse.builder()
                                .knowledgeBlock(knowledgeBlock)
                                .course(course)
                                .build();

                knowledgeBlockCourseRepository.save(relationship);
        }

        public void unassignCourseFromKnowledgeBlock(Long relationshipId) {
                KnowledgeBlockCourse relationship = knowledgeBlockCourseRepository.findById(relationshipId)
                                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found"));
                knowledgeBlockCourseRepository.delete(relationship);
        }

        public void unassignCourseFromKnowledgeBlock(Long knowledgeBlockId, Long courseId) {
                KnowledgeBlock kb = knowledgeBlockRepository.findById(knowledgeBlockId)
                                .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found"));
                Course course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                knowledgeBlockCourseRepository.deleteByKnowledgeBlockAndCourse(kb, course);
        }

        private FrameResponse toResponse(Frame frame) {
                List<KnowledgeBlockFrame> reqs = knowledgeBlockFrameRepository.findByFrame_Id(frame.getId());
                BigDecimal totalRequired = reqs.stream()
                                .map(r -> r.getMinimumCredits() != null ? r.getMinimumCredits() : BigDecimal.ZERO)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                FrameResponse.FrameResponseBuilder builder = FrameResponse.builder()
                                .id(frame.getId())
                                .pricePerCredit(frame.getPricePerCredit())
                                .isActive(frame.getIsActive())
                                .description(frame.getDescription())
                                .totalRequiredCredits(totalRequired);

                if (frame.getCurriculum() != null) {
                        builder.curriculumId(frame.getCurriculum().getId())
                                        .curriculumCode(frame.getCurriculum().getCode())
                                        .curriculumName(frame.getCurriculum().getName());
                }
                if (frame.getFaculty() != null) {
                        builder.facultyId(frame.getFaculty().getId())
                                        .facultyName(frame.getFaculty().getName());
                }
                if (frame.getMajor() != null) {
                        builder.majorId(frame.getMajor().getId())
                                        .majorName(frame.getMajor().getName());
                }

                List<KnowledgeBlockRequirementResponse> reqResponses = reqs.stream()
                                .map(r -> KnowledgeBlockRequirementResponse.builder()
                                                .knowledgeBlockId(r.getKnowledgeBlock().getId())
                                                .knowledgeBlockCode(r.getKnowledgeBlock().getCode())
                                                .knowledgeBlockName(r.getKnowledgeBlock().getName())
                                                .elective(r.getElective())
                                                .minimumCredits(r.getMinimumCredits())
                                                .build())
                                .collect(Collectors.toList());
                builder.requirements(reqResponses);
                return builder.build();
        }
}