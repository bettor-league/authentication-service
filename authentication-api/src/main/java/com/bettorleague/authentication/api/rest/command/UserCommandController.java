package com.bettorleague.authentication.api.rest.command;


import com.bettorleague.authentication.core.command.RegisterUserCommand;
import com.bettorleague.authentication.core.command.RemoveUserCommand;
import com.bettorleague.authentication.core.request.UserCreationRequest;
import com.bettorleague.authentication.core.response.MessageResponse;
import com.bettorleague.authentication.core.response.UserCreationResponse;
import com.bettorleague.microservice.cqrs.infrastructure.CommandDispatcher;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserCommandController {

    private final CommandDispatcher commandDispatcher;
    @PostMapping
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public ResponseEntity<UserCreationResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        final String id = UUID.randomUUID().toString();
        final RegisterUserCommand command = RegisterUserCommand.builder()
                .id(id)
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        commandDispatcher.send(command);
        final String message = "User creation request completed successfully";
        return new ResponseEntity<>(
                new UserCreationResponse(message, id),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{userId}")
    @Operation(security = @SecurityRequirement(name = "OAuth2TokenBearer"))
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable(value = "userId") String userId) {
        final RemoveUserCommand command = RemoveUserCommand.builder()
                .id(userId)
                .build();
        commandDispatcher.send(command);
        final String message = "User deletion request completed successfully";
        return new ResponseEntity<>(
                new MessageResponse(message),
                HttpStatus.OK
        );

    }

}
