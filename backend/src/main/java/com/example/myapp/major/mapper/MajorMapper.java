package com.example.myapp.major.mapper;

import com.example.myapp.major.Major;
import com.example.myapp.major.dto.MajorRequest;
import com.example.myapp.major.dto.MajorResponse;

public class MajorMapper {

    public static Major toEntity(MajorRequest r) {
        return Major.builder()
                .code(r.getCode())
                .name(r.getName())
                .description(r.getDescription())
                .pricePerCredit(r.getPricePerCredit())
                .totalCredits(r.getTotalCredits())
                .build();
    }

    public static MajorResponse toResponse(Major m) {
        MajorResponse resp = MajorResponse.builder()
                .id(m.getId())
                .code(m.getCode())
                .name(m.getName())
                .description(m.getDescription())
                .pricePerCredit(m.getPricePerCredit())
                .totalCredits(m.getTotalCredits())
                .build();
        if (m.getFaculty() != null) {
            resp.setFacultyId(m.getFaculty().getId());
            resp.setFacultyName(m.getFaculty().getName());
        }
        return resp;
    }
}


