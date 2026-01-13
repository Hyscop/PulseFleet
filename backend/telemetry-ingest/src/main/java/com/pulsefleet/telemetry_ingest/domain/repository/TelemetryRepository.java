package com.pulsefleet.telemetry_ingest.domain.repository;

import java.util.List;

import com.pulsefleet.telemetry_ingest.domain.model.Telemetry;

public interface TelemetryRepository {
    Telemetry save(Telemetry telemetry);

    List<Telemetry> findByDeviceId(String deviceId);

}
