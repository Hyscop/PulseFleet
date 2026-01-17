package com.pulsefleet.command_service.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Command {
    private final UUID id;
    private final String deviceId;
    private final CommandType type;
    private final String payload;
    private CommandStatus status;
    private final Instant createdAt;
    private Instant sentAt;
    private Instant acknowledgedAt;

    public static Command create(String deviceId, CommandType type, String payload) {
        return new Command(
                UUID.randomUUID(),
                deviceId,
                type,
                payload,
                CommandStatus.PENDING,
                Instant.now());
    }

    private Command(UUID id, String deviceId, CommandType type, String payload,
            CommandStatus status, Instant createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.payload = payload;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Command reconstitute(UUID id, String deviceId, CommandType type,
            String payload, CommandStatus status,
            Instant createdAt, Instant sentAt, Instant acknowledgedAt) {
        Command cmd = new Command(id, deviceId, type, payload, status, createdAt);
        cmd.sentAt = sentAt;
        cmd.acknowledgedAt = acknowledgedAt;
        return cmd;
    }

    public void markSent() {
        this.status = CommandStatus.SENT;
        this.sentAt = Instant.now();
    }

    public void markAcknowledged() {
        this.status = CommandStatus.ACKNOWLEDGED;
        this.acknowledgedAt = Instant.now();
    }

    public void markFailed() {
        this.status = CommandStatus.FAILED;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public CommandType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public CommandStatus getStatus() {
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
