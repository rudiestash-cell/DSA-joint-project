CREATE TABLE locations (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    type TEXT NOT NULL
);

CREATE TABLE roads (
    from_location INTEGER NOT NULL,
    to_location INTEGER NOT NULL,
    distance REAL NOT NULL CHECK(distance > 0),
    FOREIGN KEY(from_location) REFERENCES locations(id),
    FOREIGN KEY(to_location) REFERENCES locations(id)
);

CREATE TABLE service_requests (
    request_id TEXT PRIMARY KEY,
    location TEXT NOT NULL,
    category TEXT NOT NULL,
    description TEXT NOT NULL,
    urgency INTEGER NOT NULL CHECK(urgency BETWEEN 1 AND 5),
    status TEXT NOT NULL
);