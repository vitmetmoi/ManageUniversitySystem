package com.example.myapp.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.auth.dto.LoginRequest;
import com.example.myapp.auth.dto.LoginResponse;
import com.example.myapp.auth.mapper.LoginMapper;
import com.example.myapp.auth.repository.AuthRepository;
import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.common.exception.InvalidCredentialsException;
import com.example.myapp.auth.entity.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse loginService(LoginRequest req) {

        User user = getUserByEmail(req.getEmail());

        String userEncodePassword = user.getPassword();
        String rawPassword = req.getPassword();

        if (passwordEncoder.matches(rawPassword, userEncodePassword)) {
            LoginResponse response = LoginMapper.toResponse(user);
            return response;
        }

        throw new InvalidCredentialsException("Invalid email or password");

    }

    public User getUserByEmail(String email) {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("not found user by email" + email));
    }
}
