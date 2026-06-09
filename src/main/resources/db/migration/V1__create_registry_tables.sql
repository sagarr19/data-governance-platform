CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE data_sources (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    connection_host VARCHAR(255),
    connection_port INTEGER,
    database_name VARCHAR(255),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE schemas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    data_source_id UUID NOT NULL REFERENCES data_sources(id),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(data_source_id, name)
);

CREATE TABLE tables (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    schema_id UUID NOT NULL REFERENCES schemas(id),
    name VARCHAR(255) NOT NULL,
    row_count BIGINT,
    sensitivity_level VARCHAR(50) DEFAULT 'PUBLIC',
    scan_status VARCHAR(50) DEFAULT 'NOT_SCANNED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(schema_id, name)
);

CREATE TABLE columns (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    table_id UUID NOT NULL REFERENCES tables(id),
    name VARCHAR(255) NOT NULL,
    data_type VARCHAR(100),
    sensitivity_label VARCHAR(50) DEFAULT 'NONE',
    is_pii BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE(table_id, name)
);