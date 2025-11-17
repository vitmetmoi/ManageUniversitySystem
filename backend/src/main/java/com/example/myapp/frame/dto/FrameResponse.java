package com.example.myapp.frame.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrameResponse {
    private Long id;
    private Long curriculumId;
    private String curriculumCode;
    private String curriculumName;
    private Long facultyId;
    private String facultyName;
    private Long majorId;
    private String majorName;
    private BigDecimal pricePerCredit;
    private Boolean isActive;
    private String description;
    private BigDecimal totalRequiredCredits;
    private List<KnowledgeBlockRequirementResponse> requirements;
}