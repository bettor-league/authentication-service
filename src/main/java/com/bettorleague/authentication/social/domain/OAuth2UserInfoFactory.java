package com.bettorleague.authentication.social.domain;

import com.bettorleague.microservice.model.exception.BadRequestException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("facebook")) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new BadRequestException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}