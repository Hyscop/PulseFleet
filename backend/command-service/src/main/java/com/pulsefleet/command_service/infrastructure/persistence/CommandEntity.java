package com.pulsefleet.command_service.infrastructure.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "commands")
public class CommandEntity {
    @Id
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private String type;

    private String payload;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "acknowledged_at")
    private Instant acknowledgedAt;

    protected CommandEntity() {
    }

    public CommandEntity(UUID id, String deviceId, String type, String payload, String status, Instant createdAt,
            Instant sentAt, Instant acknowledgedAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.payload = payload;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.acknowledgedAt = acknowledgedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public Instant getAcknowledgedAt() {
        return acknowledgedAt;
    }
}
