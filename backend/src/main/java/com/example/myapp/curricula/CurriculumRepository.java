package com.example.myapp.curricula;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findByMajor_Id(Long majorId);
    Optional<Curriculum> findByMajor_IdAndEffectiveYear(Long majorId, Integer effectiveYear);
}


