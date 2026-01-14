package com.pulsefleet.device_registry.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsefleet.device_registry.domain.model.Device;
import com.pulsefleet.device_registry.domain.model.DeviceId;
import com.pulsefleet.device_registry.domain.repository.DeviceRepository;

@Service
@Transactional
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device registeredDevice(String name) {
        DeviceId deviceId = DeviceId.generate();
        Device device = Device.register(deviceId, name);
        return deviceRepository.save(device);
    }

    public Optional<Device> getDevice(String id) {
        DeviceId deviceId = DeviceId.from(id);
        return deviceRepository.findById(deviceId);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> activateDevice(String id) {
        DeviceId deviceId = DeviceId.from(id);
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);

        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            device.activate();
            deviceRepository.save(device);
            return Optional.of(device);
        }

        return Optional.empty();

    }

    public Optional<Device> deactivateDevice(String id) {
        DeviceId deviceId = DeviceId.from(id);
        Optional<Device> deviceOpt = deviceRepository.findById(deviceId);

        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            device.deactivate();
            deviceRepository.save(device);
            return Optional.of(device);
        }

        return Optional.empty();
    }

    public boolean deleteDevice(String id) {
        try {
            DeviceId deviceId = new DeviceId(UUID.fromString(id));
            return deviceRepository.findById(deviceId)
                    .map(device -> {
                        deviceRepository.delete(deviceId);
                        return true;
                    })
                    .orElse(false);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Optional<Device> renameDevice(String id, String newName) {
        try {
            DeviceId deviceId = new DeviceId(UUID.fromString(id));
            return deviceRepository.findById(deviceId).map(device -> {
                device.rename(newName);
                return deviceRepository.save(device);
            });
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
