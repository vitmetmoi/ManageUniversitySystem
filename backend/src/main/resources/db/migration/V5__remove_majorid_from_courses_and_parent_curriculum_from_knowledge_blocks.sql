-- Remove major_id column and foreign key constraint from courses table
ALTER TABLE courses DROP CONSTRAINT IF EXISTS fk_courses_major;
ALTER TABLE courses DROP COLUMN IF EXISTS major_id;

-- Remove curriculum_id and parent_block_id columns and foreign key constraints from knowledge_blocks table
ALTER TABLE knowledge_blocks DROP CONSTRAINT IF EXISTS fk_kb_curriculum;
ALTER TABLE knowledge_blocks DROP CONSTRAINT IF EXISTS fk_kb_parent;
ALTER TABLE knowledge_blocks DROP COLUMN IF EXISTS curriculum_id;
ALTER TABLE knowledge_blocks DROP COLUMN IF EXISTS parent_block_id;

