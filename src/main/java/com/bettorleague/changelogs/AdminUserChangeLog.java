package com.bettorleague.changelogs;

import com.bettorleague.authentication.domain.AdminCredential;
import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.User;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@ChangeLog
public class AdminUserChangeLog {

    @ChangeSet(order = "001", id = "insertAdminUser", author = "Nadjim Chabane")
    public void initAdminUser(MongoTemplate mongoTemplate,
                              PasswordEncoder passwordEncoder,
                              AdminCredential adminCredential) {

        User admin = User.builder()
                .username(Optional.ofNullable(adminCredential.getUsername()).orElse("test"))
                .password(passwordEncoder.encode(Optional.ofNullable(adminCredential.getPassword()).orElse("test")))
                .activated(true)
                .authorities(Set.of(Authorities.ROLE_USER, Authorities.ROLE_ADMIN))
                .build();

        mongoTemplate.save(admin);
    }

}