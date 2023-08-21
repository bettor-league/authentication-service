package com.bettorleague.authentication.api.infrastructure.command;

import com.bettorleague.authentication.core.command.RegisterUserCommand;
import com.bettorleague.authentication.core.command.RemoveUserCommand;

public interface UserCommandHandler {
    void handle(RegisterUserCommand command);

    void handle(RemoveUserCommand command);
}
