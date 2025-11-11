package com.example.myapp.frame.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrameAssignmentRequest {

    @NotNull(message = "Knowledge block ID is required")
    private Long knowledgeBlockId;

    @NotNull(message = "Target ID is required")
    private Long targetId; // Can be course ID or major ID
}

