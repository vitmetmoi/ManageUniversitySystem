package com.example.myapp.knowledgeblocks;

import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockRequest;
import com.example.myapp.knowledgeblocks.dto.KnowledgeBlockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/knowledge-blocks")
@RequiredArgsConstructor
public class KnowledgeBlockController {

    private final KnowledgeBlockService knowledgeBlockService;

    @GetMapping
    public List<KnowledgeBlockResponse> getAll() {
        return knowledgeBlockService.getAll();
    }

    @PostMapping
    public KnowledgeBlockResponse create(@Valid @RequestBody KnowledgeBlockRequest request) {
        return knowledgeBlockService.create(request);
    }

    @PutMapping("/{id}")
    public KnowledgeBlockResponse update(@PathVariable Long id, @Valid @RequestBody KnowledgeBlockRequest request) {
        return knowledgeBlockService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        knowledgeBlockService.delete(id);
    }
}


