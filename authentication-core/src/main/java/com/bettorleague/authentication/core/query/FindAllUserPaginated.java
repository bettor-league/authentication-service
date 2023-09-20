package com.bettorleague.authentication.core.query;

import com.bettorleague.authentication.core.model.User;
import com.bettorleague.microservice.model.query.FindAllPaginated;
import org.springframework.data.domain.Pageable;

public class FindAllUserPaginated extends FindAllPaginated<User> {
    public FindAllUserPaginated(Pageable pageable) {
        super(pageable);
    }
}
