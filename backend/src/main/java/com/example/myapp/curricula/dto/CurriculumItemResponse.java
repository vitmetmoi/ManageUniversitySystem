package com.example.myapp.curricula.dto;

import com.example.myapp.courses.dto.CourseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurriculumItemResponse {
    private Long id;
    private CourseResponse course;
    private Integer semester;
    private Double credits;
    private Boolean isRequired;
    private Long knowledgeBlockId;
    private Integer sequenceOrder;
    private String notes;
}


