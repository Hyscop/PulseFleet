package com.pulsefleet.command_service.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.pulsefleet.command_service.domain.model.Command;

public interface CommandRepository {

    Command save(Command command);

    Optional<Command> findById(UUID id);

    List<Command> findByDeviceId(String deviceId);
}
