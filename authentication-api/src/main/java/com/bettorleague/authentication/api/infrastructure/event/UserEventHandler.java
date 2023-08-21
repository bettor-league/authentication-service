package com.bettorleague.authentication.api.infrastructure.event;

import com.bettorleague.authentication.core.event.UserRegisteredEvent;
import com.bettorleague.authentication.core.event.UserRemovedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);

    void on(UserRemovedEvent event);
}
