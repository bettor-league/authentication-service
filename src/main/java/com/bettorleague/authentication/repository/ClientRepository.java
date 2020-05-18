package com.bettorleague.authentication.repository;

import com.bettorleague.authentication.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
