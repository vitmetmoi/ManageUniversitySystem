package com.example.myapp.frame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrameAssignmentResponse {

    private Long knowledgeBlockId;
    private String knowledgeBlockCode;
    private String knowledgeBlockName;
    private List<AssignedItem> assignedItems;
    private List<UnassignedItem> unassignedItems;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AssignedItem {
        private Long id;
        private String code;
        private String name;
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnassignedItem {
        private Long id;
        private String code;
        private String name;
        private String description;
    }
}

