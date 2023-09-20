package com.bettorleague.authentication.api.infrastructure;

import com.bettorleague.authentication.api.service.ClientService;
import com.bettorleague.authentication.api.service.UserService;
import com.bettorleague.authentication.core.model.Client;
import com.bettorleague.authentication.core.model.User;
import com.bettorleague.authentication.core.query.FindAllClientPaginated;
import com.bettorleague.authentication.core.query.FindClientByIdentifier;
import com.bettorleague.microservice.cqrs.annotations.HandleQuery;
import com.bettorleague.microservice.model.exception.ResourceNotFoundException;
import com.bettorleague.microservice.model.query.FindAllPaginated;
import com.bettorleague.microservice.model.query.FindByIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ClientQueryHandler {
    final ClientService clientService;
    @HandleQuery
    public Page<Client> handle(FindAllClientPaginated query) {
        final Pageable pageable = query.getPageable();
        return clientService.findAll(pageable);
    }

    @HandleQuery
    public Client handle(FindClientByIdentifier query){
        final String clientId = query.getIdentifier();
        return clientService.findByIdentifier(clientId).orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientId));
    }
}
