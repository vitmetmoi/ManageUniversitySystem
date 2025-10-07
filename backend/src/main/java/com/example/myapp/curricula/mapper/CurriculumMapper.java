package com.example.myapp.curricula.mapper;

import com.example.myapp.curricula.Curriculum;
import com.example.myapp.curricula.CurriculumItem;
import com.example.myapp.curricula.dto.CurriculumItemResponse;
import com.example.myapp.curricula.dto.CurriculumRequest;
import com.example.myapp.curricula.dto.CurriculumResponse;
import com.example.myapp.courses.mapper.CourseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CurriculumMapper {
    public static Curriculum toEntity(CurriculumRequest r) {
        return Curriculum.builder()
                .code(r.getCode())
                .name(r.getName())
                .effectiveYear(r.getEffectiveYear())
                .totalCredits(r.getTotalCredits())
                .description(r.getDescription())
                .build();
    }

    public static void updateEntity(Curriculum c, CurriculumRequest r) {
        c.setCode(r.getCode());
        c.setName(r.getName());
        c.setEffectiveYear(r.getEffectiveYear());
        c.setTotalCredits(r.getTotalCredits());
        c.setDescription(r.getDescription());
    }

    public static CurriculumResponse toResponse(Curriculum c) {
        CurriculumResponse.CurriculumResponseBuilder b = CurriculumResponse.builder()
                .id(c.getId())
                .code(c.getCode())
                .name(c.getName())
                .effectiveYear(c.getEffectiveYear())
                .totalCredits(c.getTotalCredits())
                .description(c.getDescription());
        if (c.getMajor() != null) {
            b.majorId(c.getMajor().getId());
            b.majorName(c.getMajor().getName());
        }
        return b.build();
    }

    public static CurriculumItemResponse toItemResponse(CurriculumItem item) {
        return CurriculumItemResponse.builder()
                .id(item.getId())
                .course(CourseMapper.toResponse(item.getCourse()))
                .semester(item.getSemester())
                .credits(item.getCredits())
                .isRequired(item.getIsRequired())
                .knowledgeBlockId(item.getKnowledgeBlock() != null ? item.getKnowledgeBlock().getId() : null)
                .sequenceOrder(item.getSequenceOrder())
                .notes(item.getNotes())
                .build();
    }

    public static List<CurriculumItemResponse> toItemResponses(List<CurriculumItem> items) {
        return items.stream().map(CurriculumMapper::toItemResponse).collect(Collectors.toList());
    }
}


