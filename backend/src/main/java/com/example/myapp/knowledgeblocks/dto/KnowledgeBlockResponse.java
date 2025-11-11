package com.example.myapp.knowledgeblocks.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KnowledgeBlockResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean elective;
    private Integer childrenCount;
}


