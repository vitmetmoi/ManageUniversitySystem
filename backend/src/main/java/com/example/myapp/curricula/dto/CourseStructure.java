package com.example.myapp.curricula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseStructure {
    private Long id;
    private String name;
    private String code;
    private Double credits;
    private Integer semester;
    private Boolean isRequired;
    private Long prerequisiteCourseId;
    private String prerequisiteCourseName;
    private Integer sequenceOrder;
    private String notes;
}


