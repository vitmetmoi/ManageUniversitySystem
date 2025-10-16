package com.example.myapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private Long id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String status;
    private Long facultyId;
    private String facultyName;
    private Long majorId;
    private String majorName;
}


