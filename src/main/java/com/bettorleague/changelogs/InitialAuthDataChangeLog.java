package com.bettorleague.changelogs;

import com.bettorleague.authentication.domain.Authorities;
import com.bettorleague.authentication.domain.Client;
import com.bettorleague.authentication.domain.EnvSecret;
import com.bettorleague.authentication.domain.User;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@ChangeLog
public class InitialAuthDataChangeLog {

    @ChangeSet(order = "001", id = "insertAdminUser", author = "Nadjim Chabane")
    public void insertAdminUser(MongoTemplate mongoTemplate,
                              PasswordEncoder passwordEncoder,
                              EnvSecret envSecret) {

        User admin = User.builder()
                .username(Optional.ofNullable(envSecret.getAdminPassword()).orElse("test"))
                .password(passwordEncoder.encode(Optional.ofNullable(envSecret.getAdminPassword()).orElse("test")))
                .activated(true)
                .authorities(Set.of(Authorities.ROLE_USER, Authorities.ROLE_ADMIN))
                .build();

        mongoTemplate.save(admin);
    }


    @ChangeSet(order = "002", id = "insertUiClientDetails", author = "Nadjim Chabane")
    public void insertUiClientDetails(MongoTemplate mongoTemplate,
                                      EnvSecret envSecret) {
        Client uiClientDetails = new Client();
        uiClientDetails.setClientId(envSecret.getUiClientId());
        uiClientDetails.setClientSecret(envSecret.getUiClientSecret());
        uiClientDetails.setScopes("ui");
        uiClientDetails.setGrantTypes("refresh_token,password");

        mongoTemplate.save(uiClientDetails);
    }

    @ChangeSet(order = "002", id = "insertServerClientDetails", author = "Nadjim Chabane")
    public void insertServerClientDetails(MongoTemplate mongoTemplate,
                                          EnvSecret envSecret) {
        Client serverClientDetails = new Client();
        serverClientDetails.setClientId(envSecret.getServerClientId());
        serverClientDetails.setClientSecret(envSecret.getServerClientSecret());
        serverClientDetails.setScopes("server");
        serverClientDetails.setGrantTypes("refresh_token,client_credentials");

        mongoTemplate.save(serverClientDetails);
    }

}
