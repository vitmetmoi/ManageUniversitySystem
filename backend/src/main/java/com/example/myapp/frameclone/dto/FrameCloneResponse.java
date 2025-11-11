package com.example.myapp.frameclone.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FrameCloneResponse {
    private Long id;
    private Long sourceMajorId;
    private String sourceMajorName;
    private Long targetMajorId;
    private String targetMajorName;
    private Long createdByUserId;
    private LocalDateTime clonedAt;
}


