package com.example.myapp.courses.mapper;

import com.example.myapp.courses.Course;
import com.example.myapp.courses.dto.CourseRequest;
import com.example.myapp.courses.dto.CourseResponse;

public class CourseMapper {

    public static Course toEntity(CourseRequest r) {
        return Course.builder()
                .code(r.getCode())
                .name(r.getName())
                .description(r.getDescription())
                .credits(r.getCredits())
                .creditTheory(r.getCreditTheory())
                .praticeTheory(r.getPraticeTheory())
                .build();
    }

    public static void updateEntity(Course c, CourseRequest r) {
        c.setCode(r.getCode());
        c.setName(r.getName());
        c.setDescription(r.getDescription());
        c.setCredits(r.getCredits());
        c.setCreditTheory(r.getCreditTheory());
        c.setPraticeTheory(r.getPraticeTheory());
    }

    public static CourseResponse toResponse(Course c) {
        CourseResponse.CourseResponseBuilder b = CourseResponse.builder()
                .id(c.getId())
                .code(c.getCode())
                .name(c.getName())
                .description(c.getDescription())
                .credits(c.getCredits())
                .creditTheory(c.getCreditTheory())
                .praticeTheory(c.getPraticeTheory());
        if (c.getCourseElective() != null) {
            b.courseElectiveId(c.getCourseElective().getId());
        }
        if (c.getCourseParallel() != null) {
            b.courseParallelId(c.getCourseParallel().getId());
        }
        if (c.getCoursePrevious() != null) {
            b.coursePreviousId(c.getCoursePrevious().getId());
        }
        return b.build();
    }
}
