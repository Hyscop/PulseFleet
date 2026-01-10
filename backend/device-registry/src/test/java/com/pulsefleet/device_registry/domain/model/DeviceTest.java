package com.pulsefleet.device_registry.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Device aggregate unit tests
 */
class DeviceTest {

    @Nested
    @DisplayName("Device Registration")
    class Registration {

        @Test
        @DisplayName("should create device with REGISTERED status")
        void shouldCreateDeviceWithRegisteredStatus() {
            // Give
            DeviceId deviceId = DeviceId.generate();
            String name = "Temp Sensor 01";

            // When

            Device device = Device.register(deviceId, name);

            // Then
            assertEquals(deviceId, device.getDeviceId());
            assertEquals(name, device.getName());
            assertEquals(DeviceStatus.REGISTERED, device.getStatus());
            assertNotNull(device.getRegisteredAt());
        }

        @Test
        @DisplayName("should reject empty device name")
        void shouldRejectEmptyDeviceName() {
            DeviceId deviceId = DeviceId.generate();
            assertThrows(IllegalArgumentException.class, () -> Device.register(deviceId, ""));
            assertThrows(IllegalArgumentException.class, () -> Device.register(deviceId, "      "));
            assertThrows(IllegalArgumentException.class, () -> Device.register(deviceId, null));

        }

    }

    @Nested
    @DisplayName("Device Activiton")
    class Activation {
        @Test
        @DisplayName("should activate registered device")
        void shouldActivateRegisteredDevice() {
            Device device = Device.register(DeviceId.generate(), "Senson 01");

            device.activate();

            assertEquals(DeviceStatus.ACTIVATED, device.getStatus());
        }

        @Test
        @DisplayName("activation should be idempotent")
        void activationShouldBeIdempotent() {
            Device device = Device.register(DeviceId.generate(), "Senson 02");

            device.activate();
            Instant firstModified = device.getLastModifiedAt();

            // Should not throw error when activated more than 1
            device.activate();

            assertEquals(DeviceStatus.ACTIVATED, device.getStatus());
        }
    }

    @Nested
    @DisplayName("Device Deactivation")
    class Deactivation {

        @Test
        @DisplayName("should deactivate actived device")
        void shouldDeactivateActivatedDevice() {
            Device device = Device.register(DeviceId.generate(), "Sensor 03");

            device.activate();

            device.deactivate();

            assertEquals(DeviceStatus.DEACTIVATED, device.getStatus());
        }

        @Test
        @DisplayName("should not allow deactiviting device that was never activated")
        void shouldNotAllowDeactivatingNeverActivatedDevice() {
            Device device = Device.register(DeviceId.generate(), "Sensor 04");

            assertThrows(IllegalStateException.class, () -> device.deactivate());
        }

        @Test
        @DisplayName("should allow reactivaiting deacitve device")
        void shouldAllowReactivatingDeactivatedDevice() {
            Device device = Device.register(DeviceId.generate(), "Sensor 05");

            device.activate();
            device.deactivate();
            device.activate();

            assertEquals(DeviceStatus.ACTIVATED, device.getStatus());

        }

    }

}
