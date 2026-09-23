-- V5: Create tickets table with booking reference and check-in tracking

CREATE TABLE tickets (
    id BIGSERIAL PRIMARY KEY,
    ticket_tier_id BIGINT NOT NULL REFERENCES ticket_tiers(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    ticket_code VARCHAR(100) NOT NULL UNIQUE,
    booking_reference VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    checked_in_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_tickets_ticket_tier ON tickets(ticket_tier_id);
CREATE INDEX idx_tickets_user ON tickets(user_id);
CREATE INDEX idx_tickets_booking_reference ON tickets(booking_reference);
CREATE INDEX idx_tickets_code ON tickets(ticket_code);