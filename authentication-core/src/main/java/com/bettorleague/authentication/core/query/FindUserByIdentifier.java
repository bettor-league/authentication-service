package com.bettorleague.authentication.core.query;

import com.bettorleague.authentication.core.model.User;
import com.bettorleague.microservice.model.query.FindByIdentifier;

public class FindUserByIdentifier extends FindByIdentifier<User> {
    public FindUserByIdentifier(String identifier) {
        super(identifier);
    }
}
