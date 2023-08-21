package com.bettorleague.authentication.api.repository;


import com.bettorleague.authentication.core.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {
    Page<Client> findAll(Pageable pageable);

    Optional<Client> findByClientId(String clientId);
}
