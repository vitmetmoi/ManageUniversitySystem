package com.example.myapp.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
    private Long facultyId;
    private Long majorId;
    private String firstName;
    private String lastName;
    private String status; // ACTIVE, INACTIVE
}
