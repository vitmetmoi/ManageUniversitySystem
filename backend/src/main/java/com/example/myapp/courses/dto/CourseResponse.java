package com.example.myapp.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Double credits;
    private Long facultyId;
    private String facultyName;
    private Long majorId;
    private String majorName;
}


