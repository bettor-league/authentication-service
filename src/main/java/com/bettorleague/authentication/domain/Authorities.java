package com.bettorleague.authentication.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authorities implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
