package com.example.myapp.curricula.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurriculumRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotNull
    private Long majorId;
    @NotNull
    private Integer effectiveYear;
    @Min(0)
    private Double totalCredits;
    private String description;
}


