package com.pulsefleet.telemetry_ingest.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsefleet.telemetry_ingest.domain.repository.TelemetryRepository;
import com.pulsefleet.telemetry_ingest.infrastructure.web.dto.TelemetryResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryRepository telemetryRepository;

    public TelemetryController(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<TelemetryResponse>> getByDevice(@PathVariable String deviceId) {
        List<TelemetryResponse> telemetry = telemetryRepository.findByDeviceId(deviceId).stream()
                .map(TelemetryResponse::from).toList();

        return ResponseEntity.ok(telemetry);

    }

}
