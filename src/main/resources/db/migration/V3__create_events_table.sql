-- V3: Create events table
CREATE TABLE events (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        slug VARCHAR(120) UNIQUE NOT NULL,
                        description TEXT,
                        things_to_know TEXT,
                        venue_name VARCHAR(255),
                        location VARCHAR(255),
                        start_time TIMESTAMP NOT NULL,
                        end_time TIMESTAMP NOT NULL,
                        banner_url VARCHAR(508),
                        status VARCHAR(50) NOT NULL CHECK (status IN ('DRAFT', 'PUBLISHED', 'CANCELLED')) DEFAULT 'DRAFT',
                        is_featured BOOLEAN NOT NULL DEFAULT FALSE,
                        view_count INT NOT NULL DEFAULT 0,
                        published_at TIMESTAMP,
                        is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                        age_restriction VARCHAR(20),
                        organizer_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                        category_id BIGINT NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX idx_events_slug ON events(slug);
CREATE INDEX idx_events_category ON events(category_id);
CREATE INDEX idx_events_organizer ON events(organizer_id);
CREATE INDEX idx_events_status ON events(status);
CREATE INDEX idx_events_featured ON events(is_featured);
CREATE INDEX idx_events_start_time ON events(start_time);
CREATE INDEX idx_events_is_deleted ON events(is_deleted);

-- Composite Index for high-performance frontend feeds (fetching published, non-deleted upcoming events)
CREATE INDEX idx_events_published_timeline ON events(status, start_time) WHERE is_deleted = FALSE;