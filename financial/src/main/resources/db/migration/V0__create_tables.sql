CREATE TABLE payment_transactions (
    id UUID PRIMARY KEY,
    booking_id UUID NOT NULL,
    analysis TEXT NOT NULL,
    total_value DECIMAL NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_update TIMESTAMP,
    version INTEGER
);