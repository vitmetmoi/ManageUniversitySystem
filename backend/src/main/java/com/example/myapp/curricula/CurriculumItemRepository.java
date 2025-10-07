package com.example.myapp.curricula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurriculumItemRepository extends JpaRepository<CurriculumItem, Long> {
    List<CurriculumItem> findByCurriculum_IdOrderBySemesterAscSequenceOrderAsc(Long curriculumId);
    List<CurriculumItem> findByKnowledgeBlock_IdOrderBySequenceOrderAsc(Long knowledgeBlockId);
    Optional<CurriculumItem> findByCurriculum_IdAndCourse_Id(Long curriculumId, Long courseId);
}


