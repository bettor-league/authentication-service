package com.bettorleague.authentication.service.impl;

import com.bettorleague.authentication.repository.ClientRepository;
import com.bettorleague.authentication.service.ClientService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        return clientRepository.findByClientId(clientId).orElseThrow(IllegalArgumentException::new);
    }
}
