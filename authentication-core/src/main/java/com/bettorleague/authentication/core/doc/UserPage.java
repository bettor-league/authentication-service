package com.bettorleague.authentication.core.doc;

import com.bettorleague.authentication.core.model.User;
import com.bettorleague.microservice.model.doc.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public class UserPage extends Page<User> {
    public UserPage(List<User> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
