CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    per_night_value DECIMAL NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_update TIMESTAMP,
    version INTEGER
);

CREATE TABLE bookings (
   id UUID PRIMARY KEY,
   id_room UUID NOT NULL REFERENCES rooms(id),
   guest_email TEXT NOT NULL,
   creation_date TIMESTAMP NOT NULL,
   last_update TIMESTAMP,
   version INTEGER
);

CREATE TABLE booking_dates (
      id UUID PRIMARY KEY,
      id_booking UUID NOT NULL REFERENCES bookings(id),
      date DATE NOT NULL,
      version INTEGER,
      UNIQUE (id_booking, date)
);