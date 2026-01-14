package com.pulsefleet.device_registry.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pulsefleet.device_registry.domain.model.Device;
import com.pulsefleet.device_registry.domain.model.DeviceId;
import com.pulsefleet.device_registry.domain.model.DeviceStatus;
import com.pulsefleet.device_registry.domain.repository.DeviceRepository;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final JpaDeviceRepository jpaRepository;

    public DeviceRepositoryImpl(JpaDeviceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @SuppressWarnings("null")
    @Override
    public Device save(Device device) {
        DeviceEntity entity = toEntity(device);
        jpaRepository.save(entity);
        return device;
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Device> findById(DeviceId deviceId) {
        return jpaRepository.findById(deviceId.getValue()).map(this::toDomain);
    }

    @Override
    public List<Device> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @SuppressWarnings("null")
    @Override
    public void delete(DeviceId deviceId) {
        jpaRepository.deleteById(deviceId.getValue());
    }

    @SuppressWarnings("null")
    @Override
    public boolean exists(DeviceId deviceId) {
        return jpaRepository.existsById(deviceId.getValue());
    }

    private DeviceEntity toEntity(Device device) {
        return new DeviceEntity(
                device.getDeviceId().getValue(),
                device.getName(),
                device.getStatus().name(),
                device.getRegisteredAt(),
                device.getLastModifiedAt());
    }

    private Device toDomain(DeviceEntity entity) {
        return Device.reconstitute(
                new DeviceId(entity.getId()),
                entity.getName(),
                DeviceStatus.valueOf(entity.getStatus()),
                entity.getRegisteredAt(),
                entity.getLastModifiedAt());
    }

}
