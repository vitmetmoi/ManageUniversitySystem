package com.example.myapp.courses.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(0)
    private Double creditTheory;
    @Min(0)
    private Double credits;
    private Double praticeTheory;
    private Long courseElectiveId;
    private Long courseParallelId;
    private Long coursePreviousId;
}
