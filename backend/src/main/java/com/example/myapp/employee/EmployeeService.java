package com.example.myapp.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myapp.auth.entity.User;
import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.employee.dto.EmployeeRequest;
import com.example.myapp.employee.dto.EmployeeResponse;
import com.example.myapp.employee.mapper.EmployeeMapper;
import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.FacultyRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;

    public List<EmployeeResponse> findAll(Long facultyId, Long majorId) {
        List<User> users;
        if (facultyId != null) users = employeeRepository.findByFacultyId(facultyId);
        else if (majorId != null) users = employeeRepository.findByMajorId(majorId);
        else users = employeeRepository.findAll();
        return users.stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
    }

    public EmployeeResponse findById(Long id) {
        User u = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        return EmployeeMapper.toResponse(u);
    }

    public EmployeeResponse create(EmployeeRequest r) {
        User u = new User();
        EmployeeMapper.applyRequest(u, r);
        if (r.getPassword() != null) u.setPassword(passwordEncoder.encode(r.getPassword()));
        u.setRole(3);
        if (r.getFacultyId() != null) {
            Faculty f = facultyRepository.findById(r.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + r.getFacultyId()));
            u.setFaculty(f);
        }
        if (r.getMajorId() != null) {
            Major m = majorRepository.findById(r.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + r.getMajorId()));
            u.setMajor(m);
        }
        return EmployeeMapper.toResponse(employeeRepository.save(u));
    }

    public EmployeeResponse update(Long id, EmployeeRequest r) {
        User u = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        EmployeeMapper.applyRequest(u, r);
        if (r.getPassword() != null && !r.getPassword().isEmpty()) {
            u.setPassword(passwordEncoder.encode(r.getPassword()));
        }
        if (r.getFacultyId() != null) {
            Faculty f = facultyRepository.findById(r.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + r.getFacultyId()));
            u.setFaculty(f);
        }
        if (r.getMajorId() != null) {
            Major m = majorRepository.findById(r.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + r.getMajorId()));
            u.setMajor(m);
        }
        return EmployeeMapper.toResponse(employeeRepository.save(u));
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id);
    }
}


