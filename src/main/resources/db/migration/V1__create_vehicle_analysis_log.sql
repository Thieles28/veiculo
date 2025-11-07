CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE vehicle_analysis_log (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    id_input_type VARCHAR(16) NOT NULL,
    id_input_value TEXT NOT NULL,
    vin_canonical VARCHAR(17) NOT NULL,
    supplier_calls JSONB NOT NULL,
    has_constraints BOOLEAN NOT NULL,
    estimated_cost_cents BIGINT,
    trace_id VARCHAR(64)
);
CREATE INDEX idx_vehicle_analysis_log_vin ON vehicle_analysis_log (vin_canonical);
CREATE INDEX idx_vehicle_analysis_log_timestamp ON vehicle_analysis_log (timestamp);