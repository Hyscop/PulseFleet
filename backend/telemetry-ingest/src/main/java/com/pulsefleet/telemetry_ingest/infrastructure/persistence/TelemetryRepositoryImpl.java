package com.pulsefleet.telemetry_ingest.infrastructure.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pulsefleet.telemetry_ingest.domain.model.Telemetry;
import com.pulsefleet.telemetry_ingest.domain.repository.TelemetryRepository;

@Repository
public class TelemetryRepositoryImpl implements TelemetryRepository {

    private final JpaTelemetryRepository jpaRepository;

    public TelemetryRepositoryImpl(JpaTelemetryRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Telemetry save(Telemetry telemetry) {

        TelemetryEntity entity = new TelemetryEntity(
                telemetry.getDeviceId(),
                telemetry.getTemperature(),
                telemetry.getBattery(),
                telemetry.getReceivedAt());

        jpaRepository.save(entity);
        return telemetry;
    }

    @Override
    public List<Telemetry> findByDeviceId(String deviceId) {
        return jpaRepository.findByDeviceIdOrderByReceivedAtDesc(deviceId).stream().map(
                e -> Telemetry.reconstitute(e.getDeviceId(), e.getTemperature(), e.getBattery(), e.getReceivedAt()))
                .toList();
    }

}
