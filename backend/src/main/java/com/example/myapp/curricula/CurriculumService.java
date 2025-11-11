package com.example.myapp.curricula;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.curricula.dto.CurriculumItemRequest;
import com.example.myapp.curricula.dto.CurriculumItemResponse;
import com.example.myapp.curricula.dto.CurriculumRequest;
import com.example.myapp.curricula.dto.CurriculumResponse;
import com.example.myapp.curricula.mapper.CurriculumMapper;
import com.example.myapp.courses.Course;
import com.example.myapp.courses.CourseRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final CurriculumItemRepository itemRepository;
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;

    public List<CurriculumResponse> getByMajor(Long majorId) {
        return curriculumRepository.findByMajor_Id(majorId).stream()
                .map(CurriculumMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CurriculumResponse getById(Long id) {
        Curriculum c = curriculumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id " + id));
        CurriculumResponse resp = CurriculumMapper.toResponse(c);
        List<CurriculumItemResponse> items = CurriculumMapper.toItemResponses(
                itemRepository.findByCurriculum_IdOrderBySemesterAscSequenceOrderAsc(id)
        );
        resp.setItems(items);
        return resp;
    }

    public CurriculumResponse create(CurriculumRequest req) {
        curriculumRepository.findByMajor_IdAndEffectiveYear(req.getMajorId(), req.getEffectiveYear())
                .ifPresent(x -> { throw new DataIntegrityViolationException("Curriculum for major and year already exists"); });
        Major major = majorRepository.findById(req.getMajorId())
                .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + req.getMajorId()));
        Curriculum c = CurriculumMapper.toEntity(req);
        c.setMajor(major);
        return CurriculumMapper.toResponse(curriculumRepository.save(c));
    }

    public CurriculumResponse update(Long id, CurriculumRequest req) {
        Curriculum c = curriculumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id " + id));
        curriculumRepository.findByMajor_IdAndEffectiveYear(req.getMajorId(), req.getEffectiveYear())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DataIntegrityViolationException("Curriculum for major and year already exists");
                    }
                });
        Major major = majorRepository.findById(req.getMajorId())
                .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + req.getMajorId()));
        CurriculumMapper.updateEntity(c, req);
        c.setMajor(major);
        return CurriculumMapper.toResponse(curriculumRepository.save(c));
    }

    public void delete(Long id) {
        if (!curriculumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curriculum not found with id " + id);
        }
        curriculumRepository.deleteById(id);
    }

    public CurriculumItemResponse addItem(Long curriculumId, CurriculumItemRequest req) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id " + curriculumId));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseId()));

        itemRepository.findByCurriculum_IdAndCourse_Id(curriculumId, req.getCourseId())
                .ifPresent(x -> { throw new DataIntegrityViolationException("Course already exists in curriculum"); });

        CurriculumItem item = CurriculumItem.builder()
                .curriculum(curriculum)
                .course(course)
                .semester(req.getSemester())
                .credits(req.getCredits())
                .isRequired(req.getIsRequired())
                .sequenceOrder(req.getSequenceOrder() == null ? 0 : req.getSequenceOrder())
                .notes(req.getNotes())
                .build();
        if (req.getKnowledgeBlockId() != null) {
            KnowledgeBlock kb = new KnowledgeBlock();
            kb.setId(req.getKnowledgeBlockId());
            item.setKnowledgeBlock(kb);
        }
        CurriculumItem saved = itemRepository.save(item);

        // total credits moved to Major; curriculum no longer stores total credits
        return CurriculumMapper.toItemResponse(saved);
    }

    public CurriculumItemResponse updateItem(Long curriculumId, Long itemId, CurriculumItemRequest req) {
        CurriculumItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum item not found with id " + itemId));
        if (!item.getCurriculum().getId().equals(curriculumId)) {
            throw new DataIntegrityViolationException("Item does not belong to curriculum");
        }
        if (!item.getCourse().getId().equals(req.getCourseId())) {
            Course course = courseRepository.findById(req.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + req.getCourseId()));
            item.setCourse(course);
        }
        item.setSemester(req.getSemester());
        item.setCredits(req.getCredits());
        item.setIsRequired(req.getIsRequired());
        item.setSequenceOrder(req.getSequenceOrder() == null ? 0 : req.getSequenceOrder());
        item.setNotes(req.getNotes());
        if (req.getKnowledgeBlockId() != null) {
            KnowledgeBlock kb = new KnowledgeBlock();
            kb.setId(req.getKnowledgeBlockId());
            item.setKnowledgeBlock(kb);
        } else {
            item.setKnowledgeBlock(null);
        }
        CurriculumItem saved = itemRepository.save(item);
        // total credits moved to Major; curriculum no longer stores total credits
        return CurriculumMapper.toItemResponse(saved);
    }

    public void deleteItem(Long curriculumId, Long itemId) {
        CurriculumItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum item not found with id " + itemId));
        if (!item.getCurriculum().getId().equals(curriculumId)) {
            throw new DataIntegrityViolationException("Item does not belong to curriculum");
        }
        itemRepository.deleteById(itemId);
        // total credits moved to Major; curriculum no longer stores total credits
    }

    public CurriculumResponse getFullStructure(Long curriculumId) {
        Curriculum c = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id " + curriculumId));
        CurriculumResponse resp = CurriculumMapper.toResponse(c);
        List<CurriculumItemResponse> items = CurriculumMapper.toItemResponses(
                itemRepository.findByCurriculum_IdOrderBySemesterAscSequenceOrderAsc(curriculumId)
        );

        // Grouping example (by semester) if needed by caller
        Map<Integer, List<CurriculumItemResponse>> grouped = items.stream()
                .collect(Collectors.groupingBy(CurriculumItemResponse::getSemester));
        // For now, we still attach flat list; callers can group client-side
        resp.setItems(items);
        return resp;
    }

    // removed recomputeTotalCredits: curriculum no longer stores total credits
}


