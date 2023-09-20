package com.bettorleague.authentication.core.doc;

import com.bettorleague.authentication.core.model.Client;
import com.bettorleague.microservice.model.doc.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ClientPage extends Page<Client> {
    public ClientPage(List<Client> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
