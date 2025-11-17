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
    private Double creditTheory;
    private Double praticeTheory;
    private Long courseElectiveId;
    private Long courseParallelId;
    private Long coursePreviousId;
}
