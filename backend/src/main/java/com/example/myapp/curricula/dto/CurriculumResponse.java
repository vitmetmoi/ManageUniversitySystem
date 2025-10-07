package com.example.myapp.curricula.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CurriculumResponse {
    private Long id;
    private String code;
    private String name;
    private Long majorId;
    private String majorName;
    private Integer effectiveYear;
    private Double totalCredits;
    private String description;
    private List<CurriculumItemResponse> items;
}


