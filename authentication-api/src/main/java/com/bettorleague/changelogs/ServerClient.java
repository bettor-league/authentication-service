package com.bettorleague.changelogs;

import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.Client;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@ChangeUnit(id = "server-client", order = "002", author = ServerClient.AUTHOR)
public class ServerClient {
    public static final String AUTHOR = "Nadjim Chabane";
    public static final String CLIENT_ID = "bettor-league.client.server.id";
    public static final String CLIENT_SECRET = "bettor-league.client.server.secret";
    public static final String CLIENT_SCOPE = "bettor-league.client.server.scope";
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;
    private final Environment environment;

    @Execution
    public void insertServerClient() {
        final String clientId = ofNullable(environment.getProperty(CLIENT_ID))
                .orElseThrow();
        final String clientSecret = ofNullable(environment.getProperty(CLIENT_SECRET))
                .orElseThrow();
        final Client client = Client
                .builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(passwordEncoder.encode(clientSecret))
                .authorizationGrantTypes(Set.of(AuthorizationGrantType.CLIENT_CREDENTIALS))
                .scopes(Set.of(Authority.READ, Authority.WRITE))
                .redirectUris(Set.of("http://127.0.0.1:5000/authorized"))
                .build();
        mongoTemplate.save(client);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection("clients");
    }

}
