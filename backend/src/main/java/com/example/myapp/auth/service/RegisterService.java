package com.example.myapp.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.auth.dto.RegisterRequest;
import com.example.myapp.auth.dto.RegisterResponse;
import com.example.myapp.auth.entity.User;
import com.example.myapp.auth.repository.AuthRepository;
import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.FacultyRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import com.example.myapp.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final AuthRepository registerRepository;
    private final PasswordEncoder passwordEncoder;
    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;

    public RegisterResponse create(RegisterRequest req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setUserName(req.getUserName());
        user.setRole(3);

        if (req.getFacultyId() != null) {
            Faculty f = facultyRepository.findById(req.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + req.getFacultyId()));
            user.setFaculty(f);
        }

        if (req.getMajorId() != null) {
            Major m = majorRepository.findById(req.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + req.getMajorId()));
            user.setMajor(m);
        }

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        if (req.getStatus() != null) {
            try {
                user.setStatus(User.Status.valueOf(req.getStatus()));
            } catch (IllegalArgumentException e) {
                user.setStatus(User.Status.ACTIVE);
            }
        }

        registerRepository.save(user);

        return new RegisterResponse();
    }
}
