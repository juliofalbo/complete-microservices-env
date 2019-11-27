CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    per_night_value BIGINT NOT NULL,
    unity TEXT NOT NULL,
    version BIGINT,
    active BOOLEAN NOT NULL
);