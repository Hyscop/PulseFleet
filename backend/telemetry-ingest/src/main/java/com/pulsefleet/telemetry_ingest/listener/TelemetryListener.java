package com.pulsefleet.telemetry_ingest.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulsefleet.telemetry_ingest.domain.model.Telemetry;
import com.pulsefleet.telemetry_ingest.domain.repository.TelemetryRepository;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class TelemetryListener {

    private final TelemetryRepository telemetryRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TelemetryListener(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload().toString();

        System.out.println("=== TELEMETRY RECEIVED ===");
        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + payload);

        try {

            String deviceId = topic.split("/")[1];

            JsonNode json = objectMapper.readTree(payload);
            Double temp = json.has("temp") ? json.get("temp").asDouble() : null;
            Integer battery = json.has("battery") ? json.get("battery").asInt() : null;

            Telemetry telemetry = new Telemetry(deviceId, temp, battery);
            telemetryRepository.save(telemetry);

            System.out.println("Saved: deviceId=" + deviceId + ", temp=" + temp + ", battery=" + battery);
        } catch (Exception e) {
            System.err.println("Failed to process telemetry: " + e.getMessage());
        }

        System.out.println("==========================");
    }
}
