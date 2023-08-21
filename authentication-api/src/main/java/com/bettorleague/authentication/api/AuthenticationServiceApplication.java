package com.bettorleague.authentication.api;

import com.bettorleague.authentication.api.infrastructure.command.UserCommandHandler;
import com.bettorleague.authentication.api.infrastructure.query.UserQueryHandler;
import com.bettorleague.authentication.core.command.RegisterUserCommand;
import com.bettorleague.authentication.core.command.RemoveUserCommand;
import com.bettorleague.authentication.core.query.FindAllUsersPaginatedQuery;
import com.bettorleague.authentication.core.query.FindAllUsersQuery;
import com.bettorleague.authentication.core.query.FindUserByIdQuery;
import com.bettorleague.microservice.common.CommonMicroservice;
import com.bettorleague.microservice.cqrs.EventMicroservice;
import com.bettorleague.microservice.cqrs.infrastructure.CommandDispatcher;
import com.bettorleague.microservice.cqrs.infrastructure.QueryDispatcher;
import com.bettorleague.microservice.mongo.MongoMicroservice;
import com.bettorleague.microservice.swagger.SwaggerMicroservice;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@EventMicroservice
@MongoMicroservice
@CommonMicroservice
@SwaggerMicroservice
@SpringBootApplication
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = "com.bettorleague.authentication.api.repository")
@OpenAPIDefinition(info = @Info(title = "Authentication API", version = "1.0", description = "Documentation Authentication API v1.0"))
public class AuthenticationServiceApplication {
	private final CommandDispatcher commandDispatcher;
	private final UserCommandHandler commandHandler;
	private final QueryDispatcher queryDispatcher;
	private final UserQueryHandler queryHandler;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(RegisterUserCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(RemoveUserCommand.class, commandHandler::handle);

		queryDispatcher.registerHandler(FindAllUsersQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindAllUsersPaginatedQuery.class, queryHandler::handle);
		queryDispatcher.registerHandler(FindUserByIdQuery.class, queryHandler::handle);
	}


}
