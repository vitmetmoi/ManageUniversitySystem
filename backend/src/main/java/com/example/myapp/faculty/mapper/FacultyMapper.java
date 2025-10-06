package com.example.myapp.faculty.mapper;

import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.dto.FacultyRequest;
import com.example.myapp.faculty.dto.FacultyResponse;

public class FacultyMapper {
    public static Faculty toEntity(FacultyRequest r) {
        Faculty f = new Faculty();
        f.setCode(r.getCode());
        f.setName(r.getName());
        f.setDescription(r.getDescription());
        return f;
    }

    public static FacultyResponse toResponse(Faculty f) {
        FacultyResponse resp = new FacultyResponse();
        resp.setId(f.getId());
        resp.setCode(f.getCode());
        resp.setName(f.getName());
        resp.setDescription(f.getDescription());
        return resp;
    }
}


