package com.pulsefleet.telemetry_ingest.infrastructure.web.dto;

import java.time.Instant;

import com.pulsefleet.telemetry_ingest.domain.model.Telemetry;

public record TelemetryResponse(String deviceId,
        Double temperature,
        Integer battery,
        Instant receivedAt) {

    public static TelemetryResponse from(Telemetry telemetry) {
        return new TelemetryResponse(telemetry.getDeviceId(), telemetry.getTemperature(), telemetry.getBattery(),
                telemetry.getReceivedAt());
    }

}
