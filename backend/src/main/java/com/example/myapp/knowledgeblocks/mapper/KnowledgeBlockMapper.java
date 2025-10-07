package com.example.myapp.knowledgeblocks.mapper;

import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockRequest;
import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockResponse;

public class KnowledgeBlockMapper {
    public static KnowledgeBlock toEntity(KnowledgeBlockRequest r) {
        return KnowledgeBlock.builder()
                .code(r.getCode())
                .name(r.getName())
                .description(r.getDescription())
                .sequenceOrder(r.getSequenceOrder())
                .build();
    }

    public static void updateEntity(KnowledgeBlock kb, KnowledgeBlockRequest r) {
        kb.setCode(r.getCode());
        kb.setName(r.getName());
        kb.setDescription(r.getDescription());
        kb.setSequenceOrder(r.getSequenceOrder());
    }

    public static KnowledgeBlockResponse toResponse(KnowledgeBlock kb, int childrenCount) {
        return KnowledgeBlockResponse.builder()
                .id(kb.getId())
                .code(kb.getCode())
                .name(kb.getName())
                .description(kb.getDescription())
                .curriculumId(kb.getCurriculum() != null ? kb.getCurriculum().getId() : null)
                .parentBlockId(kb.getParentBlock() != null ? kb.getParentBlock().getId() : null)
                .childrenCount(childrenCount)
                .build();
    }
}


