-- V4: Create ticket_tiers table for event ticketing

CREATE TABLE ticket_tiers(
                    id BIGSERIAL PRIMARY KEY,
                    event_id BIGINT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                    name VARCHAR(100) NOT NULL,
                    description TEXT,
                    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
                    capacity INT NOT NULL CHECK (capacity > 0),
                    sold_count INT NOT NULL DEFAULT 0 CHECK (sold_count >= 0),
                    max_per_order INT DEFAULT 10 CHECK (max_per_order > 0),
                    display_order INT NOT NULL DEFAULT 0,
                    is_active BOOLEAN NOT NULL DEFAULT TRUE,
                    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                    booking_start_time TIMESTAMP,
                    booking_end_time TIMESTAMP,
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- High-performance composite index for fetching active/non-deleted tiers for a specific event
CREATE INDEX idx_ticket_tiers_event_active_lookup
    ON ticket_tiers(event_id, is_active, is_deleted);