package com.example.myapp.curricula.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurriculumItemRequest {
    @NotNull
    private Long courseId;
    @NotNull
    private Integer semester;
    @NotNull
    @Min(0)
    private Double credits;
    @NotNull
    private Boolean isRequired;
    private Long knowledgeBlockId;
    private Integer sequenceOrder;
    private String notes;
}


