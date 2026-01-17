package com.pulsefleet.command_service.infrastructure.web.dto;

import com.pulsefleet.command_service.domain.model.Command;
import java.time.Instant;

public record CommandResponse(
        String id,
        String deviceId,
        String type,
        String payload,
        String status,
        Instant createdAt,
        Instant sentAt,
        Instant acknowledgedAt) {
    public static CommandResponse from(Command cmd) {
        return new CommandResponse(
                cmd.getId().toString(),
                cmd.getDeviceId(),
                cmd.getType().name(),
                cmd.getPayload(),
                cmd.getStatus().name(),
                cmd.getCreatedAt(),
                cmd.getSentAt(),
                cmd.getAcknowledgedAt());
    }
}