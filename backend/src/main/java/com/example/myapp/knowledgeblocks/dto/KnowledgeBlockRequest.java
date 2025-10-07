package com.example.myapp.knowledgeblocks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeBlockRequest {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Long curriculumId;
    private Long parentBlockId;
    private Integer sequenceOrder;
}


