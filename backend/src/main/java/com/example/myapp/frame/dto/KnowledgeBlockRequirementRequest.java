package com.example.myapp.frame.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBlockRequirementRequest {
    private Long knowledgeBlockId;
    private Boolean elective;
    private BigDecimal minimumCredits;
}