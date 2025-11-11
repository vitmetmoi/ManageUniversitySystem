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
                .theoryHours(r.getTheoryHours())
                .practiceHours(r.getPracticeHours())
                .build();
    }

    public static void updateEntity(Course c, CourseRequest r) {
        c.setCode(r.getCode());
        c.setName(r.getName());
        c.setDescription(r.getDescription());
        c.setCredits(r.getCredits());
        c.setTheoryHours(r.getTheoryHours());
        c.setPracticeHours(r.getPracticeHours());
    }

    public static CourseResponse toResponse(Course c) {
        CourseResponse.CourseResponseBuilder b = CourseResponse.builder()
                .id(c.getId())
                .code(c.getCode())
                .name(c.getName())
                .description(c.getDescription())
                .credits(c.getCredits())
                ;
        if (c.getFaculty() != null) {
            b.facultyId(c.getFaculty().getId());
            b.facultyName(c.getFaculty().getName());
        }
        if (c.getMajor() != null) {
            b.majorId(c.getMajor().getId());
            b.majorName(c.getMajor().getName());
        }
        return b.build();
    }
}


