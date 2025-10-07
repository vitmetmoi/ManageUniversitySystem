package com.example.myapp.auth.mapper;

import com.example.myapp.auth.dto.LoginRequest;
import com.example.myapp.auth.dto.LoginResponse;
import com.example.myapp.auth.entity.User;

public class LoginMapper {
    public static User toEntity(LoginRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setRole(req.getRole());

        return user;
    }

    public static LoginResponse toResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRole(user.getRole());
        loginResponse.setUserName(user.getUserName());

        return loginResponse;
    }
}
