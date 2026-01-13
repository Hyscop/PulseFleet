package com.pulsefleet.telemetry_ingest.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTelemetryRepository extends JpaRepository<TelemetryEntity, Long> {

    List<TelemetryEntity> findByDeviceIdOrderByReceivedAtDesc(String deviceId);

}
