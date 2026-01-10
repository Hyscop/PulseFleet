package com.pulsefleet.device_registry.domain.repository;

import java.util.List;
import java.util.Optional;

import com.pulsefleet.device_registry.domain.model.Device;
import com.pulsefleet.device_registry.domain.model.DeviceId;

/**
 * Device Repository Interface
 */

public interface DeviceRepository {

    Device save(Device device);

    Optional<Device> findById(DeviceId deviceId);

    List<Device> findAll();

    void delete(DeviceId deviceId);

    boolean exists(DeviceId deviceId);

}
