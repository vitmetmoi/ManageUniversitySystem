package com.example.myapp.faculty;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.faculty.dto.*;
import com.example.myapp.faculty.mapper.FacultyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public List<FacultyResponse> getAll() {
        return facultyRepository.findAll().stream()
                .map(FacultyMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FacultyResponse getById(Long id) {
        Faculty f = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + id));
        return FacultyMapper.toResponse(f);
    }

    public FacultyResponse create(FacultyRequest req) {
        Faculty f = FacultyMapper.toEntity(req);
        return FacultyMapper.toResponse(facultyRepository.save(f));
    }

    public FacultyResponse update(Long id, FacultyRequest req) {
        Faculty f = facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + id));
        f.setCode(req.getCode());
        f.setName(req.getName());
        f.setDescription(req.getDescription());
        return FacultyMapper.toResponse(facultyRepository.save(f));
    }

    public void delete(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Faculty not found with id " + id);
        }
        facultyRepository.deleteById(id);
    }
}


