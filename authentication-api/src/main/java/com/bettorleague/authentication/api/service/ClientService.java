package com.bettorleague.authentication.api.service;

import com.bettorleague.authentication.core.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

public interface ClientService extends RegisteredClientRepository {
    Page<Client> findAll(Pageable pageable);

    Optional<Client> findByIdentifier(String id);
}
