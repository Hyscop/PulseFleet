package com.pulsefleet.telemetry_ingest.domain.model;

import java.time.Instant;

public class Telemetry {

    private final String deviceId;
    private final Double temperature;
    private final Integer battery;
    private final Instant receivedAt;

    public Telemetry(String deviceId, Double temperature, Integer battery) {
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.battery = battery;
        this.receivedAt = Instant.now();
    }

    // Reconstitution
    public static Telemetry reconstitute(String deviceId, Double temperature, Integer battery, Instant receivedAt) {
        return new Telemetry(deviceId, temperature, battery, receivedAt);
    }

    private Telemetry(String deviceId, Double temperature, Integer battery, Instant receivedAt) {
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.battery = battery;
        this.receivedAt = receivedAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Integer getBattery() {
        return battery;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

}
