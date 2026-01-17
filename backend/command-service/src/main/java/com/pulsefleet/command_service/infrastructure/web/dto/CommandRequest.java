package com.pulsefleet.command_service.infrastructure.web.dto;

public class CommandRequest {
    private String deviceId;
    private String type;
    private String payload;

    public CommandRequest() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
