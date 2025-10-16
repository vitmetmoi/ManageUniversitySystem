package com.example.myapp.knowledgeblocks;

import com.example.myapp.common.exception.ResourceNotFoundException;
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

    public List<KnowledgeBlockResponse> getAll() {
        List<KnowledgeBlock> blocks = knowledgeBlockRepository.findAll();
        return blocks.stream().map(b -> KnowledgeBlockMapper.toResponse(b, 0)).collect(Collectors.toList());
    }

    public KnowledgeBlockResponse create(KnowledgeBlockRequest req) {
        KnowledgeBlock kb = KnowledgeBlockMapper.toEntity(req);
        KnowledgeBlock saved = knowledgeBlockRepository.save(kb);
        return KnowledgeBlockMapper.toResponse(saved, 0);
    }

    public KnowledgeBlockResponse update(Long id, KnowledgeBlockRequest req) {
        KnowledgeBlock kb = knowledgeBlockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Knowledge block not found with id " + id));
        KnowledgeBlockMapper.updateEntity(kb, req);
        KnowledgeBlock saved = knowledgeBlockRepository.save(kb);
        return KnowledgeBlockMapper.toResponse(saved, 0);
    }

    public void delete(Long id) {
        if (!knowledgeBlockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Knowledge block not found with id " + id);
        }
        knowledgeBlockRepository.deleteById(id);
    }
}


