package com.pulsefleet.device_registry.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeviceRepository extends JpaRepository<DeviceEntity, UUID> {

}
