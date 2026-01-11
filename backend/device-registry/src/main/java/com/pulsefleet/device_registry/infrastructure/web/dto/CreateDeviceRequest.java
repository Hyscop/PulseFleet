package com.pulsefleet.device_registry.infrastructure.web.dto;

public class CreateDeviceRequest {
    public String name;

    public CreateDeviceRequest() {
    }

    public CreateDeviceRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
