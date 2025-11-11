package com.example.myapp.outline;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.outline.dto.OutlineRequest;
import com.example.myapp.outline.dto.OutlineResponse;
import com.example.myapp.outline.mapper.OutlineMapper;
import com.example.myapp.courses.Course;
import com.example.myapp.courses.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = RuntimeException.class)
public class OutlineService {

    private final Path uploadDir = Paths.get("uploads");
    private final OutlineRepository outlineRepository;
    private final CourseRepository courseRepository;

    {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
                
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }
    }

    public List<OutlineResponse> getAll() {
        return outlineRepository.findAll().stream()
                .map(OutlineMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OutlineResponse getById(Long id) {
        Outline outline = outlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Outline not found with id " + id));
        return OutlineMapper.toResponse(outline);
    }

    public OutlineResponse create(OutlineRequest outlineRequest) {
        try {
            MultipartFile file = outlineRequest.getFilePath();
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("File is required");
            }
            
            String originalName = file.getOriginalFilename();
            String newFileName = UUID.randomUUID() + "_" + originalName;
            Path filePath = uploadDir.resolve(newFileName);
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            Outline outline = new Outline();
            outline.setFile_path(filePath.toString());
            outline.setStatus(outlineRequest.getStatus() != null ? outlineRequest.getStatus() : "ACTIVE");
            
            if (outlineRequest.getCourse_id() != null) {
                Course course = courseRepository.findById(outlineRequest.getCourse_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + outlineRequest.getCourse_id()));
                outline.setCourse(course);
            }
            
            outline.setDescription(outlineRequest.getDescription());
            Outline saved = outlineRepository.save(outline);
            return OutlineMapper.toResponse(saved);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    public OutlineResponse update(Long id, OutlineRequest outlineRequest) {
        Outline outline = outlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Outline not found with id " + id));

        // Handle file update if provided
        if (outlineRequest.getFilePath() != null && !outlineRequest.getFilePath().isEmpty()) {
            try {
                MultipartFile file = outlineRequest.getFilePath();
                String originalName = file.getOriginalFilename();
                String newFileName = UUID.randomUUID() + "_" + originalName;
                Path filePath = uploadDir.resolve(newFileName);
                
                // Delete old file if exists
                try {
                    if (outline.getFile_path() != null) {
                        Path oldPath = Paths.get(outline.getFile_path());
                        if (Files.exists(oldPath)) {
                            Files.delete(oldPath);
                        }
                    }
                } catch (IOException e) {
                    // Log but don't fail if old file can't be deleted
                }
                
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
                outline.setFile_path(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to update file", e);
            }
        }

        // Update course if provided
        if (outlineRequest.getCourse_id() != null) {
            Course course = courseRepository.findById(outlineRequest.getCourse_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + outlineRequest.getCourse_id()));
            outline.setCourse(course);
        }

        // Update description
        if (outlineRequest.getDescription() != null) {
            outline.setDescription(outlineRequest.getDescription());
        }

        // Update status
        if (outlineRequest.getStatus() != null) {
            outline.setStatus(outlineRequest.getStatus());
        }

        Outline saved = outlineRepository.save(outline);
        return OutlineMapper.toResponse(saved);
    }

    public void delete(Long id) {
        Outline outline = outlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Outline not found with id " + id));
        
        // Delete file if exists
        try {
            if (outline.getFile_path() != null) {
                Path filePath = Paths.get(outline.getFile_path());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
        } catch (IOException e) {
            // Log but don't fail if file can't be deleted
        }
        
        outlineRepository.deleteById(id);
    }
}