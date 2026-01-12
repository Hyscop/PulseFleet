package com.pulsefleet.device_registry;

import com.pulsefleet.device_registry.infrastructure.web.dto.CreateDeviceRequest;
import com.pulsefleet.device_registry.infrastructure.web.dto.DeviceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class DeviceApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateDevice() {
        // Given
        CreateDeviceRequest request = new CreateDeviceRequest("Integration Test Sensor");

        // When
        ResponseEntity<DeviceResponse> response = restTemplate.postForEntity("/api/devices", request,
                DeviceResponse.class);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Integration Test Sensor", response.getBody().getName());
        assertEquals("REGISTERED", response.getBody().getStatus());
        assertNotNull(response.getBody().getId());

    }

    @Test
    void shouldGetDeviceById() {
        // Given
        CreateDeviceRequest request = new CreateDeviceRequest("Sensor for Get Test");
        ResponseEntity<DeviceResponse> createResponse = restTemplate.postForEntity("/api/devices", request,
                DeviceResponse.class);

        String deviceID = createResponse.getBody().getId();

        // When
        ResponseEntity<DeviceResponse> response = restTemplate.getForEntity("/api/devices/" + deviceID,
                DeviceResponse.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceID, response.getBody().getId());
        assertEquals("Sensor for Get Test", response.getBody().getName());
    }

    @Test
    void shouldReturnNotFoundForNonExistentDevice() {
        // When
        ResponseEntity<DeviceResponse> response = restTemplate
                .getForEntity("/api/devices/000000000-0000-000-0-0-0000-0000000000 ", DeviceResponse.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void shouldActivateDevice() {
        // Given
        CreateDeviceRequest request = new CreateDeviceRequest("Sensor to Activate");
        ResponseEntity<DeviceResponse> createResponse = restTemplate.postForEntity("/api/devices", request,
                DeviceResponse.class);
        String deviceId = createResponse.getBody().getId();

        // When
        ResponseEntity<DeviceResponse> response = restTemplate.postForEntity("/api/devices/" + deviceId + "/activate",
                null, DeviceResponse.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ACTIVATED", response.getBody().getStatus());
    }

    @Test
    void shouldListAllDevices() {

        // Given
        restTemplate.postForEntity("/api/devices", new CreateDeviceRequest("List Test 1"), DeviceResponse.class);
        restTemplate.postForEntity("/api/devices", new CreateDeviceRequest("List Test 2"), DeviceResponse.class);

        // When
        ResponseEntity<DeviceResponse[]> response = restTemplate.getForEntity("/api/devices", DeviceResponse[].class);

        // Then

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 2);

    }

}
