-- Create knowledgeblock_course table for many-to-many relationship
CREATE TABLE knowledgeblock_course
(
    id BIGSERIAL PRIMARY KEY,
    knowledgeblock_id BIGINT NOT NULL REFERENCES knowledge_blocks(id) ON DELETE CASCADE,
    course_id BIGINT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(knowledgeblock_id, course_id)
);

-- Create knowledgeblock_major table for many-to-many relationship
CREATE TABLE knowledgeblock_major
(
    id BIGSERIAL PRIMARY KEY,
    knowledgeblock_id BIGINT NOT NULL REFERENCES knowledge_blocks(id) ON DELETE CASCADE,
    major_id BIGINT NOT NULL REFERENCES majors(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(knowledgeblock_id, major_id)
);

-- Create indexes for better performance
CREATE INDEX idx_knowledgeblock_course_knowledgeblock_id ON knowledgeblock_course(knowledgeblock_id);
CREATE INDEX idx_knowledgeblock_course_course_id ON knowledgeblock_course(course_id);
CREATE INDEX idx_knowledgeblock_major_knowledgeblock_id ON knowledgeblock_major(knowledgeblock_id);
CREATE INDEX idx_knowledgeblock_major_major_id ON knowledgeblock_major(major_id);

-- Add foreign key constraints with proper naming
ALTER TABLE knowledgeblock_course ADD CONSTRAINT fk_knowledgeblock_course_knowledgeblock 
    FOREIGN KEY (knowledgeblock_id) REFERENCES knowledge_blocks(id) ON DELETE CASCADE;
ALTER TABLE knowledgeblock_course ADD CONSTRAINT fk_knowledgeblock_course_course 
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE;

ALTER TABLE knowledgeblock_major ADD CONSTRAINT fk_knowledgeblock_major_knowledgeblock 
    FOREIGN KEY (knowledgeblock_id) REFERENCES knowledge_blocks(id) ON DELETE CASCADE;
ALTER TABLE knowledgeblock_major ADD CONSTRAINT fk_knowledgeblock_major_major 
    FOREIGN KEY (major_id) REFERENCES majors(id) ON DELETE CASCADE;





