package com.bettorleague.authentication.core.query;

import com.bettorleague.authentication.core.model.Client;
import com.bettorleague.microservice.model.query.FindAllPaginated;
import org.springframework.data.domain.Pageable;

public class FindAllClientPaginated extends FindAllPaginated<Client> {
    public FindAllClientPaginated(Pageable pageable) {
        super(pageable);
    }
}
