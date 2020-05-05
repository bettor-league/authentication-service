package com.bettorleague.authentication.repository;

import com.bettorleague.authentication.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
