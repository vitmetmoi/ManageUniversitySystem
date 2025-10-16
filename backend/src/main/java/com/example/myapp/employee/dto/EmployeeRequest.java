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
public class EmployeeRequest {
    private String email;
    private String password; // only for create
    private String userName;
    private String firstName;
    private String lastName;
    private String status; // ACTIVE/INACTIVE
    private Long facultyId;
    private Long majorId;
}


