package com.bettorleague.changelogs;

import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.Client;
import com.bettorleague.authentication.domain.User;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@ChangeLog
public class InitialAuthDataChangeLog {

    @ChangeSet(order = "001", id = "insertAdminUser", author = "Nadjim Chabane")
    public void insertAdminUser(MongoTemplate mongoTemplate,
                                PasswordEncoder passwordEncoder,
                                Environment environment) {

        Instant now = Instant.now();
        String encodedPassword = Optional.ofNullable(environment.getProperty("bettorleague.admin.username"))
                .map(passwordEncoder::encode)
                .orElse(passwordEncoder.encode("admin"));

        User admin = User.builder()
                .id(Optional.ofNullable(environment.getProperty("bettorleague.admin.id")).orElse("000000000000000000000001"))
                .email(Optional.ofNullable(environment.getProperty("bettorleague.admin.email")).orElse("admin@admin.com"))
                .username(Optional.ofNullable(environment.getProperty("bettorleague.admin.username")).orElse("admin"))
                .password(encodedPassword)
                .activated(true)
                .authorities(Set.of(Authorities.ROLE_USER, Authorities.ROLE_ADMIN))
                .build();

        admin.setCreatedBy(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.server.client-id")).orElse("server"));
        admin.setLastModifiedBy(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.server.client-id")).orElse("server"));
        admin.setCreatedAt(now);
        admin.setLastModified(now);

        mongoTemplate.save(admin);
    }


    @ChangeSet(order = "002", id = "insertUiClientDetails", author = "Nadjim Chabane")
    public void insertUiClientDetails(MongoTemplate mongoTemplate,
                                      Environment environment) {
        Instant now = Instant.now();

        Client uiClientDetails = new Client();
        uiClientDetails.setClientId(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.ui.client-id")).orElse(""));
        uiClientDetails.setClientSecret(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.ui.client-secret")).orElse(""));
        uiClientDetails.setScopes("ui");
        uiClientDetails.setGrantTypes("refresh_token,password");

        uiClientDetails.setCreatedAt(now);
        uiClientDetails.setLastModified(now);

        mongoTemplate.save(uiClientDetails);
    }

    @ChangeSet(order = "002", id = "insertServerClientDetails", author = "Nadjim Chabane")
    public void insertServerClientDetails(MongoTemplate mongoTemplate,
                                          Environment environment) {
        Instant now = Instant.now();

        Client serverClientDetails = new Client();
        serverClientDetails.setClientId(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.server.client-id")).orElse(""));
        serverClientDetails.setClientSecret(Optional.ofNullable(environment.getProperty("bettorleague.oauth2.client.server.client-secret")).orElse(""));
        serverClientDetails.setScopes("server");
        serverClientDetails.setGrantTypes("refresh_token,client_credentials");

        serverClientDetails.setLastModified(now);
        serverClientDetails.setCreatedAt(now);

        mongoTemplate.save(serverClientDetails);
    }

}
