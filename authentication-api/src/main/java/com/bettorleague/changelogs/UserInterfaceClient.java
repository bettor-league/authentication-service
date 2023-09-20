package com.bettorleague.changelogs;

import com.bettorleague.authentication.core.model.Client;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

import static com.bettorleague.authentication.core.model.Authority.*;
import static java.util.Optional.ofNullable;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.*;

@RequiredArgsConstructor
@ChangeUnit(id = "user-interface-client", order = "004", author = ServerClient.AUTHOR)
public class UserInterfaceClient {
    public static final String AUTHOR = "Nadjim Chabane";
    public static final String CLIENT_ID = "bettor-league.client.ui.id";
    public static final String CLIENT_SECRET = "bettor-league.client.ui.secret";
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;
    private final Environment environment;

    @Execution
    public void insertUserInterfaceClient() {
        final String clientId = ofNullable(environment.getProperty(CLIENT_ID))
                .orElseThrow();
        final String clientSecret = ofNullable(environment.getProperty(CLIENT_SECRET))
                .orElseThrow();
        final Client client = Client
                .builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(passwordEncoder.encode(clientSecret))
                .authorizationGrantTypes(Set.of(AUTHORIZATION_CODE, REFRESH_TOKEN, CLIENT_CREDENTIALS))
                .scopes(Set.of(READ, OPENID))
                .redirectUris(Set.of("http://127.0.0.1:5000/authorized", "http://127.0.0.1:4000/webjars/swagger-ui/oauth2-redirect.html", "http://localhost:4000/webjars/swagger-ui/oauth2-redirect.html"))
                .build();
        mongoTemplate.save(client);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection("clients");
    }

}
