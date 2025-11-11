package com.example.myapp.courses.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Min(0)
    private Double credits;
    private Integer theoryHours;
    private Integer practiceHours;
    private Long facultyId;
    private Long majorId;
}


