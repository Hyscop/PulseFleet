package com.pulsefleet.device_registry.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsefleet.device_registry.application.DeviceService;
import com.pulsefleet.device_registry.domain.model.Device;
import com.pulsefleet.device_registry.infrastructure.web.dto.CreateDeviceRequest;
import com.pulsefleet.device_registry.infrastructure.web.dto.DeviceResponse;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevicEntity(@RequestBody CreateDeviceRequest request) {
        Device device = deviceService.registeredDevice(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(DeviceResponse.from(device));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable String id) {
        return deviceService.getDevice(id).map(device -> ResponseEntity.ok(DeviceResponse.from(device)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {

        List<DeviceResponse> devices = deviceService.getAllDevices().stream().map(DeviceResponse::from).toList();
        return ResponseEntity.ok(devices);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<DeviceResponse> activateDevic(@PathVariable String id) {
        return deviceService.activateDevice(id).map(device -> ResponseEntity.ok(DeviceResponse.from(device)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<DeviceResponse> deactivateDevice(@PathVariable String id) {
        return deviceService.deactivateDevice(id)
                .map(device -> ResponseEntity.ok(DeviceResponse.from(device)))
                .orElse(ResponseEntity.notFound().build());
    }

}
