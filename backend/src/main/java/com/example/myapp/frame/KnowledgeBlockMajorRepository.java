package com.example.myapp.frame;

import com.example.myapp.major.Major;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeBlockMajorRepository extends JpaRepository<KnowledgeBlockMajor, Long> {

    // Find by knowledge block
    List<KnowledgeBlockMajor> findByKnowledgeBlock(KnowledgeBlock knowledgeBlock);

    // Find by major
    List<KnowledgeBlockMajor> findByMajor(Major major);

    // Find by knowledge block ID
    @Query("SELECT kbm FROM KnowledgeBlockMajor kbm WHERE kbm.knowledgeBlock.id = :knowledgeBlockId")
    List<KnowledgeBlockMajor> findByKnowledgeBlockId(@Param("knowledgeBlockId") Long knowledgeBlockId);

    // Find by major ID
    @Query("SELECT kbm FROM KnowledgeBlockMajor kbm WHERE kbm.major.id = :majorId")
    List<KnowledgeBlockMajor> findByMajorId(@Param("majorId") Long majorId);

    // Find specific relationship
    Optional<KnowledgeBlockMajor> findByKnowledgeBlockAndMajor(KnowledgeBlock knowledgeBlock, Major major);

    // Check if relationship exists
    boolean existsByKnowledgeBlockAndMajor(KnowledgeBlock knowledgeBlock, Major major);

    // Delete by knowledge block and major
    void deleteByKnowledgeBlockAndMajor(KnowledgeBlock knowledgeBlock, Major major);

    // Get majors not assigned to a knowledge block
    @Query("SELECT m FROM Major m WHERE m NOT IN " +
           "(SELECT kbm.major FROM KnowledgeBlockMajor kbm WHERE kbm.knowledgeBlock.id = :knowledgeBlockId)")
    List<Major> findUnassignedMajors(@Param("knowledgeBlockId") Long knowledgeBlockId);

    // Get knowledge blocks not assigned to a major
    @Query("SELECT kb FROM KnowledgeBlock kb WHERE kb NOT IN " +
           "(SELECT kbm.knowledgeBlock FROM KnowledgeBlockMajor kbm WHERE kbm.major.id = :majorId)")
    List<KnowledgeBlock> findUnassignedKnowledgeBlocks(@Param("majorId") Long majorId);
}

