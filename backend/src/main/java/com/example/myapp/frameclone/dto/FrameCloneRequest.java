package com.example.myapp.frameclone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrameCloneRequest {
    @NotNull
    private Long sourceMajorId;
    @NotNull
    private Long targetMajorId;
    // if set true, also clone knowledgeblock_course relations
    private Boolean includeCourses;
}


