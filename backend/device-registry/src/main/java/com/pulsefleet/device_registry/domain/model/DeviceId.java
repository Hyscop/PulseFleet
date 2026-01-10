package com.pulsefleet.device_registry.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Device identity Value Object
 */
public final class DeviceId {

    private final UUID value;

    public DeviceId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("DeviceId cannot be null");
        }
        this.value = value;
    }

    public static DeviceId generate() {
        return new DeviceId(UUID.randomUUID());
    }

    public static DeviceId from(String value) {
        return new DeviceId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DeviceId deviceId = (DeviceId) o;
        return Objects.equals(value, deviceId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
