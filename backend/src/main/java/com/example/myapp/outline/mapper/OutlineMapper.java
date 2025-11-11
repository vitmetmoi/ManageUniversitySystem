package com.example.myapp.outline.mapper;

import com.example.myapp.outline.Outline;
import com.example.myapp.outline.dto.OutlineResponse;

public class OutlineMapper {
    
    public static OutlineResponse toResponse(Outline outline) {
        if (outline == null) {
            return null;
        }
        return OutlineResponse.builder()
                .id(outline.getId())
                .filePath(outline.getFile_path())
                .courseId(outline.getCourse() != null ? outline.getCourse().getId() : null)
                .courseCode(outline.getCourse() != null ? outline.getCourse().getCode() : null)
                .courseName(outline.getCourse() != null ? outline.getCourse().getName() : null)
                .description(outline.getDescription())
                .status(outline.getStatus())
                .createdAt(outline.getCreatedAt())
                .updatedAt(outline.getUpdatedAt())
                .build();
    }
}