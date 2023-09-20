package com.bettorleague.authentication.api.service.impl;

import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.Client;
import com.bettorleague.authentication.api.repository.ClientRepository;
import com.bettorleague.authentication.api.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        final Client client = registeredClientToClient(registeredClient);
        clientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(id)
                .map(this::clientToRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(this::clientToRegisteredClient)
                .orElse(null);
    }

    private RegisteredClient clientToRegisteredClient(final Client client){
        Assert.notNull(client, "client cannot be null");
        return RegisteredClient
                .withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(clientAuthenticationMethods-> clientAuthenticationMethods.addAll(Set.of(CLIENT_SECRET_POST, CLIENT_SECRET_BASIC)))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(client.getAuthorizationGrantTypes()))
                .scopes(scopes -> scopes.addAll(client.getScopes().stream().map(Authority::getAuthority).collect(Collectors.toSet())))
                .redirectUris(redirectUris -> redirectUris.addAll(client.getRedirectUris()))
                //.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }

    private Client registeredClientToClient(final RegisteredClient registeredClient){
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        return Client.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientSecret(registeredClient.getClientSecret())
                .clientName(registeredClient.getClientName())
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes())
                .scopes(registeredClient.getScopes().stream().map(Authority::valueOf).collect(Collectors.toSet()))
                .redirectUris(registeredClient.getRedirectUris())
                .build();
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Optional<Client> findByIdentifier(String id) {
        return clientRepository.findById(id);
    }
}
