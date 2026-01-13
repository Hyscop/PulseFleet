package com.pulsefleet.telemetry_ingest.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TelemetryTest {

    @Test
    void shouldCreateTelemetryWithValues() {
        Telemetry telemetry = new Telemetry("device-1", 23.5, 85);

        assertEquals("device-1", telemetry.getDeviceId());
        assertEquals(23.5, telemetry.getTemperature());
        assertEquals(85, telemetry.getBattery());
        assertNotNull(telemetry.getReceivedAt());
    }

    @Test
    void shouldAllowNullTemperature() {
        Telemetry telemetry = new Telemetry("device-2", null, 85);

        assertNull(telemetry.getTemperature());
        assertEquals(85, telemetry.getBattery());
    }

    @Test
    void shouldAllowNullBattery() {
        Telemetry telemetry = new Telemetry("device-3", 24.5, null);

        assertNull(telemetry.getBattery());
        assertEquals(24.5, telemetry.getTemperature());
    }

}
