package com.agiletv.user.application.generate_users;

import com.agiletv.shared.domain.bus.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GenerateUsersCommandHandler implements CommandHandler<GenerateUsersCommand> {
  private final GenerateUsers generateUsers;
  
  @Override
  public void handle(GenerateUsersCommand command) {
    generateUsers.generate(command.getQuantity());
  }
}
