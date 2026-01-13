package com.pulsefleet.telemetry_ingest.infrastrucutre.web;

import com.pulsefleet.telemetry_ingest.domain.model.Telemetry;
import com.pulsefleet.telemetry_ingest.domain.repository.TelemetryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TelemetryApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TelemetryRepository telemetryRepository;

    @Test
    void shouldReturnTelemetryForDevice() throws Exception {
        // Given
        telemetryRepository.save(new Telemetry("sensor-1", 25.0, 90));
        telemetryRepository.save(new Telemetry("sensor-1", 13.0, 15));

        // When Then
        mockMvc.perform(get("/api/telemetry/device/sensor-1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].deviceId").value("sensor-1"));
    }

    @Test
    void shouldReturnEmptyListForUnknownDevice() throws Exception {
        mockMvc.perform(get("/api/telemetry/device/unknown-device"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}
