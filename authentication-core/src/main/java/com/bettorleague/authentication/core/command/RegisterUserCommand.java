package com.bettorleague.authentication.core.command;

import com.bettorleague.microservice.cqrs.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class RegisterUserCommand extends BaseCommand {
    private String email;
    private String password;
}