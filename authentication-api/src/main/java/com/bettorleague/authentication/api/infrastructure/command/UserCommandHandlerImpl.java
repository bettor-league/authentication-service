package com.bettorleague.authentication.api.infrastructure.command;

import com.bettorleague.authentication.api.aggregate.UserAggregate;
import com.bettorleague.authentication.core.command.RegisterUserCommand;
import com.bettorleague.authentication.core.command.RemoveUserCommand;
import com.bettorleague.microservice.cqrs.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandHandlerImpl implements UserCommandHandler {
    private final EventSourcingHandler<UserAggregate> eventSourcingHandler;
    @Override
    public void handle(RegisterUserCommand command) {
        final UserAggregate aggregate = new UserAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handle(RemoveUserCommand command) {
        final String id = command.getId();
        final UserAggregate aggregate = eventSourcingHandler.getById(id);
        aggregate.on(command);
        eventSourcingHandler.save(aggregate);
    }
}
