package com.pulsefleet.telemetry_ingest.infrastructure.persistence;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "telemetry")
public class TelemetryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;
    private Double temperature;
    private Integer battery;
    private Instant receivedAt;

    protected TelemetryEntity() {
    }

    public TelemetryEntity(String deviceId, Double temperature, Integer battery, Instant receivedAt) {
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.battery = battery;
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
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
