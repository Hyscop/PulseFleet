package com.pulsefleet.command_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.pulsefleet.command_service.domain.model.Command;
import com.pulsefleet.command_service.domain.model.CommandStatus;
import com.pulsefleet.command_service.domain.model.CommandType;
import com.pulsefleet.command_service.domain.repository.CommandRepository;

@Repository
@SuppressWarnings("null")
public class CommandRepositoryImpl implements CommandRepository {

    private final JpaCommandRepository jpaRepository;

    public CommandRepositoryImpl(JpaCommandRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Command save(Command command) {
        CommandEntity entity = toEntity(command);
        jpaRepository.save(entity);
        return command;
    }

    @Override
    public Optional<Command> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Command> findByDeviceId(String deviceId) {
        return jpaRepository.findByDeviceId(deviceId).stream()
                .map(this::toDomain)
                .toList();
    }

    private CommandEntity toEntity(Command cmd) {
        return new CommandEntity(
                cmd.getId(),
                cmd.getDeviceId(),
                cmd.getType().name(),
                cmd.getPayload(),
                cmd.getStatus().name(),
                cmd.getCreatedAt(),
                cmd.getSentAt(),
                cmd.getAcknowledgedAt());
    }

    private Command toDomain(CommandEntity entity) {
        return Command.reconstitute(
                entity.getId(),
                entity.getDeviceId(),
                CommandType.valueOf(entity.getType()),
                entity.getPayload(),
                CommandStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getSentAt(),
                entity.getAcknowledgedAt());
    }

}
