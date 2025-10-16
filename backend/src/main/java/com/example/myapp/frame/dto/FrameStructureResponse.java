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
public class FrameStructureResponse {

    private Long majorId;
    private String majorCode;
    private String majorName;
    private List<KnowledgeBlockInfo> knowledgeBlocks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KnowledgeBlockInfo {
        private Long id;
        private String code;
        private String name;
        private Integer sequenceOrder;
        private List<CourseInfo> courses;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CourseInfo {
            private Long id;
            private String code;
            private String name;
            private Double credits;
            private Boolean elective;
        }
    }
}
