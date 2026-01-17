package com.pulsefleet.command_service.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommandRepository extends JpaRepository<CommandEntity, UUID> {

    List<CommandEntity> findByDeviceId(String deviceId);

}
