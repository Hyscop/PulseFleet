package com.pulsefleet.command_service.application;

import com.pulsefleet.command_service.domain.model.Command;
import com.pulsefleet.command_service.domain.model.CommandType;
import com.pulsefleet.command_service.domain.repository.CommandRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandService {

    private final CommandRepository commandRepository;
    private final MqttPahoMessageHandler mqttHandler;

    public CommandService(CommandRepository commandRepository, MqttPahoMessageHandler mqttHandeler) {
        this.commandRepository = commandRepository;
        this.mqttHandler = mqttHandeler;
    }

    public Command sendCommand(String deviceId, CommandType type, String payload) {
        Command command = Command.create(deviceId, type, payload);
        commandRepository.save(command);

        String topic = "devices/" + deviceId + "/commands";
        String message = String.format("{\"commandId\":\"%s\",\"type\":\"%s\",\"payload\":%s}", command.getId(),
                type.name(), payload != null ? payload : "null");

        try {
            mqttHandler.handleMessage(MessageBuilder
                    .withPayload(message)
                    .setHeader("mqtt_topic", topic)
                    .build());
            command.markSent();
        } catch (Exception e) {
            command.markFailed();
        }
        commandRepository.save(command);
        return command;
    }

    public Optional<Command> findById(UUID id) {
        return commandRepository.findById(id);

    }

    public List<Command> findByDeviceId(String deviceId) {
        return commandRepository.findByDeviceId(deviceId);
    }

    public Optional<Command> acknowledgeCommand(UUID commandId) {
        return commandRepository.findById(commandId).map(cmd -> {
            cmd.markAcknowledged();
            return commandRepository.save(cmd);
        });
    }

}
