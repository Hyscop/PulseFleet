package com.pulsefleet.command_service.infrastructure.web;

import com.pulsefleet.command_service.application.CommandService;
import com.pulsefleet.command_service.domain.model.CommandType;
import com.pulsefleet.command_service.infrastructure.web.dto.CommandRequest;
import com.pulsefleet.command_service.infrastructure.web.dto.CommandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<CommandResponse> sendCommand(@RequestBody CommandRequest request) {
        try {
            CommandType type = CommandType.valueOf(request.getType().toUpperCase());
            var command = commandService.sendCommand(
                    request.getDeviceId(),
                    type,
                    request.getPayload());
            return ResponseEntity.ok(CommandResponse.from(command));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandResponse> getCommand(@PathVariable String id) {
        return commandService.findById(UUID.fromString(id))
                .map(cmd -> ResponseEntity.ok(CommandResponse.from(cmd)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<CommandResponse>> getCommandsByDevice(@PathVariable String deviceId) {
        var commands = commandService.findByDeviceId(deviceId).stream()
                .map(CommandResponse::from)
                .toList();
        return ResponseEntity.ok(commands);
    }

    @PostMapping("/{id}/ack")
    public ResponseEntity<CommandResponse> acknowledgeCommand(@PathVariable String id) {
        return commandService.acknowledgeCommand(UUID.fromString(id))
                .map(cmd -> ResponseEntity.ok(CommandResponse.from(cmd)))
                .orElse(ResponseEntity.notFound().build());
    }

}
