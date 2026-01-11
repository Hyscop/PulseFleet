package com.pulsefleet.device_registry.infrastructure.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt;

    @Column(name = "last_modified_at", nullable = false)
    private Instant lastModifiedAt;

    protected DeviceEntity() {
    }

    public DeviceEntity(UUID id, String name, String status, Instant registeredAt, Instant lastModifiedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.registeredAt = registeredAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public Instant getLastModifiedAt() {
        return lastModifiedAt;
    }

    // ----------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLastModifiedAt(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

}
