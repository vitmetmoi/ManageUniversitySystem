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
}
