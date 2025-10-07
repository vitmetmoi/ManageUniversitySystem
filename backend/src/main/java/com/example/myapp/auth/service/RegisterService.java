package com.example.myapp.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.auth.dto.RegisterRequest;
import com.example.myapp.auth.dto.RegisterResponse;
import com.example.myapp.auth.entity.User;
import com.example.myapp.auth.repository.AuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final AuthRepository registerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse create(RegisterRequest req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setUserName(req.getUserName());
        user.setRole(3);

        registerRepository.save(user);

        return new RegisterResponse();
    }
}
