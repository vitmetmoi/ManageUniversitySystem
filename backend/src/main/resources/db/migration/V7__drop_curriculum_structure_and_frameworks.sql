-- Drop tables created in V6 migration in reverse order of dependencies
-- Drop curriculum_clones table
DROP TABLE IF EXISTS curriculum_clones;

-- Drop framework_blocks table
DROP TABLE IF EXISTS framework_blocks;

-- Drop curriculum_frameworks table
DROP TABLE IF EXISTS curriculum_frameworks;

-- Drop course_relationships table
DROP TABLE IF EXISTS course_relationships;

-- Remove prerequisite_course_id column and its foreign key constraint from curriculum_items
ALTER TABLE curriculum_items 
DROP CONSTRAINT IF EXISTS fk_ci_prerequisite;

ALTER TABLE curriculum_items 
DROP COLUMN IF EXISTS prerequisite_course_id;

