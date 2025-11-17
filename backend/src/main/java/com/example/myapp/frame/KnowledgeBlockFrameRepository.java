package com.example.myapp.frame;

import com.example.myapp.frame.KnowledgeBlockFrame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeBlockFrameRepository extends JpaRepository<KnowledgeBlockFrame, Long> {
    List<KnowledgeBlockFrame> findByFrame_Id(Long frameId);
    void deleteByFrame_Id(Long frameId);
}