package com.example.myapp.frame.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameCreateRequest {
    private Long curriculumId;
    private Long facultyId;
    private Long majorId;
    private BigDecimal pricePerCredit;
    private Boolean isActive;
    private String description;
    private List<KnowledgeBlockRequirementRequest> requirements;
}