package com.example.myapp.frameclone;

import com.example.myapp.frameclone.dto.FrameCloneRequest;
import com.example.myapp.frameclone.dto.FrameCloneResponse;
import com.example.myapp.major.MajorRepository;
import com.example.myapp.major.dto.MajorResponse;
import com.example.myapp.major.mapper.MajorMapper;
import com.example.myapp.auth.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/frame-clone")
@RequiredArgsConstructor
public class FrameCloneController {

    private final FrameCloneService frameCloneService;
    private final MajorRepository majorRepository;

    @GetMapping("/majors")
    public List<MajorResponse> listMajors() {
        return majorRepository.findAll().stream()
                .map(MajorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FrameCloneResponse clone(@Valid @RequestBody FrameCloneRequest request, Principal principal) {
        // In a real app, resolve current user from security context
        User currentUser = null;
        return frameCloneService.cloneMajorFrame(request, currentUser);
    }

    @GetMapping("/history")
    public List<FrameCloneResponse> history() {
        return frameCloneService.history();
    }
}


