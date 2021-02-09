package com.agiletv.user.application.update_user;

import com.agiletv.shared.domain.bus.command.CommandHandler;
import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand> {
  private final UpdateUser updateUser;
  
  @Override
  public void handle(UpdateUserCommand command) {
    Name name = new Name(command.getName(), command.getFirstSurname());
    Gender gender = Gender.from(command.getGender());
  
    updateUser.update(
      command.getUsername(),
      name,
      command.getEmail(),
      gender,
      command.getPictureUrl());
  }
}
