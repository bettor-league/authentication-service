package com.bettorleague.authentication.core.query;

import com.bettorleague.authentication.core.model.Client;
import com.bettorleague.microservice.model.query.FindByIdentifier;

public class FindClientByIdentifier extends FindByIdentifier<Client> {
    public FindClientByIdentifier(String identifier) {
        super(identifier);
    }
}
