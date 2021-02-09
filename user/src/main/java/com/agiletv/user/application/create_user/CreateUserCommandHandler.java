package com.agiletv.user.application.create_user;

import com.agiletv.shared.domain.bus.command.CommandHandler;
import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
  private final CreateUser createUser;
  
  @Override
  public void handle(CreateUserCommand command) {
    Name name = new Name(command.getName(), command.getFirstSurname());
    Gender gender = Gender.from(command.getGender());
  
    createUser.create(
      command.getUsername(),
      name,
      command.getEmail(),
      gender,
      command.getPictureUrl());
  }
}
