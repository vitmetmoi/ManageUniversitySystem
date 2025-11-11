package com.example.myapp.major;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.faculty.Faculty;
import com.example.myapp.faculty.FacultyRepository;
import com.example.myapp.major.dto.*;
import com.example.myapp.major.mapper.MajorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;

    public List<MajorResponse> getAll() {
        return majorRepository.findAll().stream()
                .map(MajorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public MajorResponse getById(Long id) {
        Major m = majorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + id));
        return MajorMapper.toResponse(m);
    }

    public MajorResponse create(MajorRequest req) {
        Faculty faculty = facultyRepository.findById(req.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + req.getFacultyId()));
        Major m = MajorMapper.toEntity(req);
        m.setFaculty(faculty);
        return MajorMapper.toResponse(majorRepository.save(m));
    }

    public MajorResponse update(Long id, MajorRequest req) {
        Major m = majorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Major not found with id " + id));
        m.setCode(req.getCode());
        m.setName(req.getName());
        m.setDescription(req.getDescription());
        m.setPricePerCredit(req.getPricePerCredit());
        m.setTotalCredits(req.getTotalCredits());

        if (req.getFacultyId() != null && !req.getFacultyId().equals(m.getFaculty().getId())) {
            Faculty faculty = facultyRepository.findById(req.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id " + req.getFacultyId()));
            m.setFaculty(faculty);
        }

        return MajorMapper.toResponse(majorRepository.save(m));
    }

    public void delete(Long id) {
        if (!majorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Major not found with id " + id);
        }
        majorRepository.deleteById(id);
    }

    public List<MajorResponse> findByFaculty(Long facultyId) {
        return majorRepository.findByFacultyId(facultyId).stream()
                .map(MajorMapper::toResponse)
                .collect(Collectors.toList());
    }
}


