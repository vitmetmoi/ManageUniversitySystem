package com.example.myapp.courses.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Double credits;
    private Boolean elective;
    private Long facultyId;
    private String facultyName;
    private Long majorId;
    private String majorName;
}


