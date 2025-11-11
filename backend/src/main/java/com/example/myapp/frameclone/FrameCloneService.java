package com.example.myapp.frameclone;

import com.example.myapp.auth.entity.User;
import com.example.myapp.frame.KnowledgeBlockMajor;
import com.example.myapp.frame.KnowledgeBlockMajorRepository;
import com.example.myapp.frameclone.dto.FrameCloneRequest;
import com.example.myapp.frameclone.dto.FrameCloneResponse;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import com.example.myapp.knowledgeblocks.KnowledgeBlockRepository;
import com.example.myapp.major.Major;
import com.example.myapp.major.MajorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FrameCloneService {

    private final MajorRepository majorRepository;
    private final KnowledgeBlockMajorRepository kbMajorRepository;
    // repositories for major and relations
    private final KnowledgeBlockRepository knowledgeBlockRepository;
    private final FrameCloneRepository frameCloneRepository;

    @Transactional
    public FrameCloneResponse cloneMajorFrame(FrameCloneRequest request, User currentUser) {
        Major source = majorRepository.findById(request.getSourceMajorId())
                .orElseThrow(() -> new IllegalArgumentException("Source major not found"));
        Major target = majorRepository.findById(request.getTargetMajorId())
                .orElseThrow(() -> new IllegalArgumentException("Target major not found"));

        // Fetch KBs linked to source major
        List<KnowledgeBlockMajor> sourceLinks = kbMajorRepository.findByMajorId(source.getId());

        // Duplicate KB-major relations to target major
        for (KnowledgeBlockMajor link : sourceLinks) {
            KnowledgeBlock kb = link.getKnowledgeBlock();
            if (!kbMajorRepository.existsByKnowledgeBlockAndMajor(kb, target)) {
                KnowledgeBlockMajor newLink = KnowledgeBlockMajor.builder()
                        .knowledgeBlock(kb)
                        .major(target)
                        .build();
                kbMajorRepository.save(newLink);
            }
            // Optionally clone KB-course relations (already at KB level, so nothing per-major to copy)
            // We keep as-is since KB->Course is global to KB, not per-major
        }

        FrameClone saved = frameCloneRepository.save(FrameClone.builder()
                .sourceMajor(source)
                .targetMajor(target)
                .createdBy(currentUser)
                .build());

        return FrameCloneResponse.builder()
                .id(saved.getId())
                .sourceMajorId(source.getId())
                .sourceMajorName(source.getName())
                .targetMajorId(target.getId())
                .targetMajorName(target.getName())
                .createdByUserId(currentUser != null ? currentUser.getId() : null)
                .clonedAt(saved.getClonedAt())
                .build();
    }

    @Transactional
    public List<FrameCloneResponse> history() {
        return frameCloneRepository.findAll().stream().map(fc -> FrameCloneResponse.builder()
                .id(fc.getId())
                .sourceMajorId(fc.getSourceMajor() != null ? fc.getSourceMajor().getId() : null)
                .sourceMajorName(fc.getSourceMajor() != null ? fc.getSourceMajor().getName() : null)
                .targetMajorId(fc.getTargetMajor() != null ? fc.getTargetMajor().getId() : null)
                .targetMajorName(fc.getTargetMajor() != null ? fc.getTargetMajor().getName() : null)
                .createdByUserId(fc.getCreatedBy() != null ? fc.getCreatedBy().getId() : null)
                .clonedAt(fc.getClonedAt())
                .build()).collect(Collectors.toList());
    }
}


