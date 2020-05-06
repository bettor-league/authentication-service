package com.bettorleague.authentication.util;

import com.bettorleague.authentication.domain.AuthProvider;
import com.bettorleague.authentication.domain.FacebookOAuth2UserInfo;
import com.bettorleague.authentication.domain.GoogleOAuth2UserInfo;
import com.bettorleague.authentication.domain.OAuth2UserInfo;
import com.bettorleague.authentication.exception.OAuth2AuthenticationProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
