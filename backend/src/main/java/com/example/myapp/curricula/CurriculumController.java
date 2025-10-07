package com.example.myapp.curricula;

import com.example.myapp.curricula.dto.CurriculumItemRequest;
import com.example.myapp.curricula.dto.CurriculumItemResponse;
import com.example.myapp.curricula.dto.CurriculumRequest;
import com.example.myapp.curricula.dto.CurriculumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/curricula")
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;

    @GetMapping
    public List<CurriculumResponse> getByMajor(@RequestParam(required = false) Long majorId) {
        if (majorId == null) return List.of();
        return curriculumService.getByMajor(majorId);
    }

    @GetMapping("/{id}")
    public CurriculumResponse getById(@PathVariable Long id) {
        return curriculumService.getById(id);
    }

    @PostMapping
    public CurriculumResponse create(@Valid @RequestBody CurriculumRequest request) {
        return curriculumService.create(request);
    }

    @PutMapping("/{id}")
    public CurriculumResponse update(@PathVariable Long id, @Valid @RequestBody CurriculumRequest request) {
        return curriculumService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        curriculumService.delete(id);
    }

    @PostMapping("/{id}/items")
    public CurriculumItemResponse addItem(@PathVariable Long id, @Valid @RequestBody CurriculumItemRequest request) {
        return curriculumService.addItem(id, request);
    }

    @PutMapping("/{id}/items/{itemId}")
    public CurriculumItemResponse updateItem(@PathVariable Long id, @PathVariable Long itemId, @Valid @RequestBody CurriculumItemRequest request) {
        return curriculumService.updateItem(id, itemId, request);
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public void deleteItem(@PathVariable Long id, @PathVariable Long itemId) {
        curriculumService.deleteItem(id, itemId);
    }
}


