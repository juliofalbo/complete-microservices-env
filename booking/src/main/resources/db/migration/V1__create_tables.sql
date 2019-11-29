CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    per_night_value DECIMAL NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_update TIMESTAMP,
    version INTEGER
);

CREATE TABLE bookings (
   id_room UUID NOT NULL REFERENCES rooms(id),
   guest_email TEXT NOT NULL,
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   creation_date TIMESTAMP NOT NULL,
   last_update TIMESTAMP,
   version INTEGER,
   PRIMARY KEY(id_room, start_date, end_date)
);