package com.example.myapp.frame.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBlockRequirementResponse {
    private Long knowledgeBlockId;
    private String knowledgeBlockCode;
    private String knowledgeBlockName;
    private Boolean elective;
    private BigDecimal minimumCredits;
}