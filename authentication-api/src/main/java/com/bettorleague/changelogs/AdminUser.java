package com.bettorleague.changelogs;

import com.bettorleague.authentication.core.model.Authority;
import com.bettorleague.authentication.core.model.User;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;
@RequiredArgsConstructor
@ChangeUnit(id="admin-user", order = "001", author = AdminUser.AUTHOR)
public class AdminUser {

    public static final String AUTHOR = "Nadjim Chabane";
    public static final String ADMIN_USER_EMAIL = "bettor-league.user.admin.email";
    public static final String ADMIN_USER_PASSWORD = "bettor-league.user.admin.password";
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;
    private final Environment environment;
    @Execution
    public void insertAdminUser() {
        final Instant now = Instant.now();
        final String email = ofNullable(environment.getProperty(ADMIN_USER_EMAIL))
                .orElseThrow();
        final String encodedPassword = ofNullable(environment.getProperty(ADMIN_USER_PASSWORD))
                .map(passwordEncoder::encode)
                .orElseThrow();
        final String id = UUID.randomUUID().toString();

        final User admin = User.builder()
                .id(id)
                .email(email)
                .password(encodedPassword)
                .activated(true)
                .authorities(Set.of(Authority.USER, Authority.ADMIN))
                .build();
        admin.setCreatedAt(now);
        admin.setLastModified(now);

        mongoTemplate.save(admin);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection("users");
    }

}
