CREATE TABLE
IF NOT EXISTS OUTLINE
(
    id BIGSERIAL PRIMARY KEY,
    course_id BIGSERIAL REFERENCES courses
(id) ON
DELETE CASCADE,
    file_path VARCHAR(300),
    description TEXT,
    status VARCHAR
(20),
    created_at TIMESTAMP
DEFAULT NOW
(),
    updated_at TIMESTAMP DEFAULT NOW
(),
    UNIQUE
(id,course_id)

)