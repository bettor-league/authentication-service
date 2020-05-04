package com.bettorleague.authentication.domain;

import lombok.Data;

@Data
public class AdminCredential {
    private final String username;
    private final String password;

    public AdminCredential(String username,
                           String password){
        this.username = username;
        this.password = password;
    }
}
