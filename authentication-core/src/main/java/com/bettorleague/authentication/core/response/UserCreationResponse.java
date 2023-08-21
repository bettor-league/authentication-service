package com.bettorleague.authentication.core.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationResponse extends MessageResponse {
    private String userId;

    public UserCreationResponse(String message, String id){
        super(message);
        this.userId = id;
    }

}