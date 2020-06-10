package com.bettorleague.authentication.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Resource {

    @GetMapping(value = "/success")
    public Map<String,Object> success(@RequestParam("access_token") String access_token,
                                      @RequestParam("refresh_token") String refresh_token,
                                      @RequestParam("token_type") String token_type,
                                      @RequestParam("expires_in") int expires_in) {
        Map<String,Object> result = new HashMap<>();
        result.put("access_token", access_token);
        result.put("refresh_token", refresh_token);
        result.put("token_type", token_type);
        result.put("expires_in", expires_in);
        return result;
    }

    @GetMapping(value = "/error")
    public Map<String,Object> error(@RequestParam("error") String error) {
        Map<String,Object> result = new HashMap<>();
        result.put("error", error);
        return result;
    }
}
