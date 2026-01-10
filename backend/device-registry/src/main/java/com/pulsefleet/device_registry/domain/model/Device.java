package com.pulsefleet.device_registry.domain.model;

import java.time.Instant;
import java.util.Objects;

/**
 * 
 * Device Aggregate Root
 * 
 * Invariartes
 * 
 * - deviceId cannot be null
 * - name cannot be null
 * - DEACTIVATED device can be ACTIVATED
 * - REGISTERED device cannot be DEACTIVATED directly
 * 
 * 
 */
public class Device {

    public final DeviceId deviceId;
    private String name;
    private DeviceStatus status;
    private final Instant registeredAt;
    private Instant lastModifiedAt;

    // Factory Method - new device create
    public static Device register(DeviceId deviceId, String name) {
        validateName(name);
        return new Device(deviceId, name, DeviceStatus.REGISTERED, Instant.now());
    }

    // Private constructor - just for the factory and reconstituion
    private Device(DeviceId deviceId, String name, DeviceStatus status, Instant registeredAt) {
        this.deviceId = Objects.requireNonNull(deviceId, "DeviceId cannot be null");
        this.name = name;
        this.status = status;
        this.registeredAt = registeredAt;
        this.lastModifiedAt = registeredAt;
    }

    // Reconstitution - translating db data to domain obj
    public static Device reconstitute(DeviceId deviceId, String name, DeviceStatus status, Instant registeredAt,
            Instant lastModifiedAt) {
        Device device = new Device(deviceId, name, status, registeredAt);
        device.lastModifiedAt = lastModifiedAt;
        return device;
    }

    public void activate() {
        if (this.status == DeviceStatus.ACTIVATED) {
            return;
        }

        this.status = DeviceStatus.ACTIVATED;
        this.lastModifiedAt = Instant.now();

    }

    public void deactivate() {
        if (this.status == DeviceStatus.REGISTERED) {
            throw new IllegalStateException(
                    "Cannot deactivate a device that was never activated. Activate first.");
        }

        if (this.status == DeviceStatus.DEACTIVATED) {
            return;
        }

        this.status = DeviceStatus.DEACTIVATED;
        this.lastModifiedAt = Instant.now();

    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
        this.lastModifiedAt = Instant.now();
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Device name cannot be empty");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Device name cannot exceed 255 chars");
        }
    }

    // Getters - no setters (changes are only with behavior methods)
    public DeviceId getDeviceId() {
        return deviceId;
    }

    public String getName() {
        return name;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }
}
