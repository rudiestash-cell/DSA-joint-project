PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS audit_events;
DROP TABLE IF EXISTS algorithm_runs;
DROP TABLE IF EXISTS resources;
DROP TABLE IF EXISTS service_requests;
DROP TABLE IF EXISTS roads;
DROP TABLE IF EXISTS locations;

CREATE TABLE locations (
    locationId TEXT PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    area TEXT NOT NULL,
    type TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL
);

CREATE TABLE roads (
    fromLocationId TEXT NOT NULL,
    toLocationId TEXT NOT NULL,
    distance REAL NOT NULL CHECK (distance > 0),
    travelTime REAL NOT NULL CHECK (travelTime > 0),
    roadConditionWeight REAL NOT NULL CHECK (roadConditionWeight > 0),
    PRIMARY KEY (fromLocationId, toLocationId),
    FOREIGN KEY (fromLocationId) REFERENCES locations(locationId),
    FOREIGN KEY (toLocationId) REFERENCES locations(locationId),
    CHECK (fromLocationId <> toLocationId)
);

CREATE TABLE service_requests (
    requestId TEXT PRIMARY KEY,
    source TEXT NOT NULL,
    destination TEXT NOT NULL,
    category TEXT NOT NULL,
    description TEXT,
    urgency INTEGER NOT NULL CHECK (urgency BETWEEN 1 AND 5),
    timeSubmitted TEXT NOT NULL,
    deadline TEXT NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('Pending', 'In Progress', 'Resolved')),
    FOREIGN KEY (source) REFERENCES locations(name),
    FOREIGN KEY (destination) REFERENCES locations(name)
);

CREATE TABLE resources (
    resourceId TEXT PRIMARY KEY,
    type TEXT NOT NULL,
    homeLocation TEXT NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    availabilityStatus TEXT NOT NULL,
    FOREIGN KEY (homeLocation) REFERENCES locations(locationId)
);

CREATE TABLE algorithm_runs (
    runId TEXT PRIMARY KEY,
    algorithmName TEXT NOT NULL,
    inputSize INTEGER NOT NULL CHECK (inputSize > 0),
    timeNs INTEGER NOT NULL CHECK (timeNs >= 0),
    memoryKb REAL NOT NULL CHECK (memoryKb >= 0),
    dateRun TEXT NOT NULL
);

CREATE TABLE audit_events (
    eventId INTEGER PRIMARY KEY AUTOINCREMENT,
    eventType TEXT NOT NULL,
    description TEXT NOT NULL,
    eventTime TEXT NOT NULL
);

CREATE INDEX idx_requests_urgency ON service_requests(urgency);
CREATE INDEX idx_requests_source ON service_requests(source);
CREATE INDEX idx_requests_destination ON service_requests(destination);
CREATE INDEX idx_roads_from ON roads(fromLocationId);
CREATE INDEX idx_roads_to ON roads(toLocationId);
CREATE INDEX idx_algorithm_runs_algorithm ON algorithm_runs(algorithmName);
