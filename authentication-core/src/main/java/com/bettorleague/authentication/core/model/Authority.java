package com.bettorleague.authentication.core.model;

import com.bettorleague.microservice.model.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Authority implements GrantedAuthority {

    READ(AuthorityType.SCOPE, "read"),
    WRITE(AuthorityType.SCOPE, "write"),
    OPENID(AuthorityType.SCOPE, "openid"),
    USER(AuthorityType.ROLE, "user"),
    ADMIN(AuthorityType.ROLE, "admin");

    @Getter
    private final AuthorityType type;
    @Getter
    private final String label;

    Authority(final AuthorityType type, final String label) {
        this.type = type;
        this.label = label;
    }

    @Override
    @JsonValue
    public String getAuthority() {
        return label;
    }

    @JsonCreator
    public static Authority fromLabel(String label){
        return Arrays.stream(Authority.values()).filter(predicate -> predicate.getAuthority().equals(label)).findFirst()
                .orElseThrow(() -> new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("No enum found for label : %s", label)));
    }

}
