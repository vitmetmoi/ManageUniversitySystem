package com.example.myapp.knowledgeblocks;

import com.example.myapp.common.exception.ResourceNotFoundException;
import com.example.myapp.curricula.Curriculum;
import com.example.myapp.curricula.CurriculumRepository;
import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockRequest;
import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockResponse;
import com.example.myapp.knowledgeblocks.mapper.KnowledgeBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KnowledgeBlockService {

    private final KnowledgeBlockRepository knowledgeBlockRepository;
    private final CurriculumRepository curriculumRepository;

    public List<KnowledgeBlockResponse> getByCurriculum(Long curriculumId) {
        List<KnowledgeBlock> blocks = knowledgeBlockRepository.findByCurriculum_IdOrderBySequenceOrderAsc(curriculumId);
        return blocks.stream().map(b -> KnowledgeBlockMapper.toResponse(
                b,
                knowledgeBlockRepository.findByParentBlock_IdOrderBySequenceOrderAsc(b.getId()).size()
        )).collect(Collectors.toList());
    }

    public KnowledgeBlockResponse create(KnowledgeBlockRequest req) {
        Curriculum curriculum = curriculumRepository.findById(req.getCurriculumId())
                .orElseThrow(() -> new ResourceNotFoundException("Curriculum not found with id " + req.getCurriculumId()));
        KnowledgeBlock kb = KnowledgeBlockMapper.toEntity(req);
        kb.setCurriculum(curriculum);
        if (req.getParentBlockId() != null) {
            KnowledgeBlock parent = knowledgeBlockRepository.findById(req.getParentBlockId())
                    .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found with id " + req.getParentBlockId()));
            kb.setParentBlock(parent);
        }
        KnowledgeBlock saved = knowledgeBlockRepository.save(kb);
        int childCount = knowledgeBlockRepository.findByParentBlock_IdOrderBySequenceOrderAsc(saved.getId()).size();
        return KnowledgeBlockMapper.toResponse(saved, childCount);
    }

    public KnowledgeBlockResponse update(Long id, KnowledgeBlockRequest req) {
        KnowledgeBlock kb = knowledgeBlockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found with id " + id));
        // curriculum is immutable here; only update fields and parent
        KnowledgeBlockMapper.updateEntity(kb, req);
        if (req.getParentBlockId() != null) {
            KnowledgeBlock parent = knowledgeBlockRepository.findById(req.getParentBlockId())
                    .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found with id " + req.getParentBlockId()));
            kb.setParentBlock(parent);
        } else {
            kb.setParentBlock(null);
        }
        KnowledgeBlock saved = knowledgeBlockRepository.save(kb);
        int childCount = knowledgeBlockRepository.findByParentBlock_IdOrderBySequenceOrderAsc(saved.getId()).size();
        return KnowledgeBlockMapper.toResponse(saved, childCount);
    }

    public void delete(Long id) {
        if (!knowledgeBlockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Knowledge block not found with id " + id);
        }
        knowledgeBlockRepository.deleteById(id);
    }
}


