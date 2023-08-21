package com.bettorleague.authentication.core.model;

import com.bettorleague.microservice.mongo.config.AuditedEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class Client extends AuditedEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String clientId;
    private String clientSecret;
    @Indexed(unique = true)
    private String clientName;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<Authority> scopes = new HashSet<>();
    private Set<String> redirectUris = new HashSet<>();
}
