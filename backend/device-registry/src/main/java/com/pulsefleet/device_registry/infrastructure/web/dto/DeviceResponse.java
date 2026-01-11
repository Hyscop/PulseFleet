package com.pulsefleet.device_registry.infrastructure.web.dto;

import java.time.Instant;

import com.pulsefleet.device_registry.domain.model.Device;

public class DeviceResponse {
    private String id;
    private String name;
    private String status;
    private Instant registeredAt;
    private Instant lastModifiedAt;

    public DeviceResponse() {
    }

    public static DeviceResponse from(Device device) {
        DeviceResponse response = new DeviceResponse();

        response.id = device.getDeviceId().toString();
        response.name = device.getName();
        response.status = device.getStatus().name();
        response.registeredAt = device.getRegisteredAt();
        response.lastModifiedAt = device.getLastModifiedAt();
        return response;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

}
