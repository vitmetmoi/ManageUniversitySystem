package com.example.myapp.knowledgeblocks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeBlockRepository extends JpaRepository<KnowledgeBlock, Long> {
    List<KnowledgeBlock> findByCurriculum_IdOrderBySequenceOrderAsc(Long curriculumId);
    List<KnowledgeBlock> findByParentBlock_IdOrderBySequenceOrderAsc(Long parentBlockId);
}


