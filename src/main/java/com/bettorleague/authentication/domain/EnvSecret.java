package com.bettorleague.authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvSecret {
    private String adminUsername;
    private String adminPassword;
    private String uiClientId;
    private String uiClientSecret;
    private String serverClientId;
    private String serverClientSecret;
}
