package com.bettorleague.authentication;

import com.bettorleague.microservice.common.CommonMicroservice;
import com.bettorleague.microservice.mongo.MongoMicroservice;
import com.bettorleague.microservice.swagger.SwaggerMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@CommonMicroservice
@MongoMicroservice
@SwaggerMicroservice
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Authentication API", version = "1.0", description = "Documentation Authentication API v1.0"))
public class AuthenticationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
}
