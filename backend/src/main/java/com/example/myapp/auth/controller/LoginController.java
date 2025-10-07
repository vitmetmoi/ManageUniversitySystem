package com.example.myapp.auth.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.auth.dto.LoginRequest;
import com.example.myapp.auth.dto.LoginResponse;
import com.example.myapp.auth.service.LoginService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    public final LoginService loginService;

    @PostMapping()
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return loginService.loginService(loginRequest);
    }

}
