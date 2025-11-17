ALTER TABLE courses DROP CONSTRAINT IF EXISTS fk_courses_faculty;
ALTER TABLE courses DROP CONSTRAINT IF EXISTS fk_courses_major;
ALTER TABLE courses DROP COLUMN IF EXISTS faculty_id;
ALTER TABLE courses DROP COLUMN IF EXISTS major_id;
ALTER TABLE courses DROP COLUMN IF EXISTS elective;
ALTER TABLE majors DROP COLUMN IF EXISTS price_per_credit;
ALTER TABLE courses DROP COLUMN IF EXISTS theory_hours;
ALTER TABLE courses DROP COLUMN IF EXISTS practice_hours;
ALTER TABLE majors DROP COLUMN IF EXISTS total_credits;

ALTER TABLE courses ADD COLUMN IF NOT EXISTS credit_theory NUMERIC(5,2) DEFAULT 0;
ALTER TABLE courses ADD COLUMN IF NOT EXISTS pratice_theory NUMERIC(5,2) DEFAULT 0;
ALTER TABLE courses ADD COLUMN IF NOT EXISTS course_elective_id BIGINT;
ALTER TABLE courses ADD COLUMN IF NOT EXISTS course_parallel_id BIGINT;
ALTER TABLE courses ADD COLUMN IF NOT EXISTS course_previous_id BIGINT;

-- ALTER TABLE courses ADD CONSTRAINT IF NOT EXISTS fk_courses_elective_course FOREIGN KEY (course_elective_id) REFERENCES courses(id) ON DELETE SET NULL;
-- ALTER TABLE courses ADD CONSTRAINT IF NOT EXISTS fk_courses_parallel_course FOREIGN KEY (course_parallel_id) REFERENCES courses(id) ON DELETE SET NULL;
-- ALTER TABLE courses ADD CONSTRAINT IF NOT EXISTS fk_courses_previous_course FOREIGN KEY (course_previous_id) REFERENCES courses(id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_courses_elective_id ON courses(course_elective_id);
CREATE INDEX IF NOT EXISTS idx_courses_parallel_id ON courses(course_parallel_id);
CREATE INDEX IF NOT EXISTS idx_courses_previous_id ON courses(course_previous_id);

CREATE TABLE IF NOT EXISTS frames (
    id BIGSERIAL PRIMARY KEY,
    faculty_id BIGINT REFERENCES faculties(id) ON DELETE SET NULL,
    major_id BIGINT REFERENCES majors(id) ON DELETE SET NULL,
    curriculum_id BIGINT REFERENCES curricula(id) ON DELETE SET NULL,
    price_per_credit NUMERIC(6,2),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_frames_faculty_id ON frames(faculty_id);
CREATE INDEX IF NOT EXISTS idx_frames_major_id ON frames(major_id);
CREATE INDEX IF NOT EXISTS idx_frames_curriculum_id ON frames(curriculum_id);

CREATE TABLE IF NOT EXISTS knowledgeblock_frame (
    id BIGSERIAL PRIMARY KEY,
    knowledgeblock_id BIGINT NOT NULL REFERENCES knowledge_blocks(id) ON DELETE CASCADE,
    frame_id BIGINT NOT NULL REFERENCES frames(id) ON DELETE CASCADE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    elective BOOLEAN NOT NULL DEFAULT FALSE,
    minimum_credits NUMERIC(5,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(knowledgeblock_id, frame_id)
);

CREATE INDEX IF NOT EXISTS idx_kbf_kb_id ON knowledgeblock_frame(knowledgeblock_id);
CREATE INDEX IF NOT EXISTS idx_kbf_frame_id ON knowledgeblock_frame(frame_id);

DROP TABLE IF EXISTS knowledgeblock_major CASCADE;
DROP TABLE IF EXISTS knownledge_major CASCADE;
DROP TABLE IF EXISTS frame_clones CASCADE;
DROP TABLE IF EXISTS curriculum_items CASCADE;
DROP TABLE IF EXISTS cirriculumn_items CASCADE;