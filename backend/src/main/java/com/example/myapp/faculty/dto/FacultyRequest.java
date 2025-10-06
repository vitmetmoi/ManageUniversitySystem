package com.example.myapp.faculty.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyRequest {
    private String code;
    private String name;
    private String description;
}


