package com.bettorleague.authentication.core.model;

import lombok.Getter;

public enum AuthorityType {
    SCOPE("SCOPE_", "scope"),
    ROLE("ROLE_", "role");

    @Getter
    private final String prefix;
    @Getter
    private final String attributeName;

    AuthorityType(String prefix, String attributeName){
        this.prefix = prefix;
        this.attributeName = attributeName;
    }
}
