package com.bettorleague.authentication.api;

import com.bettorleague.microservice.common.CommonMicroservice;
import com.bettorleague.microservice.cqrs.EventMicroservice;
import com.bettorleague.microservice.mongo.MongoMicroservice;
import com.bettorleague.microservice.security.config.UnprotectedPath;
import com.bettorleague.microservice.swagger.SwaggerMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EventMicroservice
@MongoMicroservice
@CommonMicroservice
@SwaggerMicroservice
@SpringBootApplication
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "com.bettorleague.authentication.api.repository")
@EnableConfigurationProperties(value = UnprotectedPath.class)
@OpenAPIDefinition(info = @Info(title = "Authentication API", version = "1.0", description = "Documentation Authentication API v1.0"))
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
