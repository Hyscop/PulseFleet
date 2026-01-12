CREATE TABLE devices(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL, 
    last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_devices_status ON devices(status);