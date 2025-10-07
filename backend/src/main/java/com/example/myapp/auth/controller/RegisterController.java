package com.example.myapp.auth.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.myapp.auth.dto.RegisterRequest;
import com.example.myapp.auth.dto.RegisterResponse;
import com.example.myapp.auth.service.RegisterService;
import com.example.myapp.faculty.FacultyRepository;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping
    public RegisterResponse create(@Valid @RequestBody RegisterRequest request) {
        return registerService.create(request);
    }

}
