package com.example.myapp.frame;

import com.example.myapp.courses.Course;
import com.example.myapp.knowledgeblocks.KnowledgeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeBlockCourseRepository extends JpaRepository<KnowledgeBlockCourse, Long> {

    // Find by knowledge block
    List<KnowledgeBlockCourse> findByKnowledgeBlock(KnowledgeBlock knowledgeBlock);

    // Find by course
    List<KnowledgeBlockCourse> findByCourse(Course course);

    // Find by knowledge block ID
    @Query("SELECT kbc FROM KnowledgeBlockCourse kbc WHERE kbc.knowledgeBlock.id = :knowledgeBlockId")
    List<KnowledgeBlockCourse> findByKnowledgeBlockId(@Param("knowledgeBlockId") Long knowledgeBlockId);

    // Find by course ID
    @Query("SELECT kbc FROM KnowledgeBlockCourse kbc WHERE kbc.course.id = :courseId")
    List<KnowledgeBlockCourse> findByCourseId(@Param("courseId") Long courseId);

    // Find specific relationship
    Optional<KnowledgeBlockCourse> findByKnowledgeBlockAndCourse(KnowledgeBlock knowledgeBlock, Course course);

    // Check if relationship exists
    boolean existsByKnowledgeBlockAndCourse(KnowledgeBlock knowledgeBlock, Course course);

    // Delete by knowledge block and course
    void deleteByKnowledgeBlockAndCourse(KnowledgeBlock knowledgeBlock, Course course);

    // Get courses not assigned to a knowledge block
    @Query("SELECT c FROM Course c WHERE c NOT IN " +
           "(SELECT kbc.course FROM KnowledgeBlockCourse kbc WHERE kbc.knowledgeBlock.id = :knowledgeBlockId)")
    List<Course> findUnassignedCourses(@Param("knowledgeBlockId") Long knowledgeBlockId);

    // Get knowledge blocks not assigned to a course
    @Query("SELECT kb FROM KnowledgeBlock kb WHERE kb NOT IN " +
           "(SELECT kbc.knowledgeBlock FROM KnowledgeBlockCourse kbc WHERE kbc.course.id = :courseId)")
    List<KnowledgeBlock> findUnassignedKnowledgeBlocks(@Param("courseId") Long courseId);
}

