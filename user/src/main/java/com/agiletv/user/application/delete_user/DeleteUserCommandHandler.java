package com.agiletv.user.application.delete_user;

import com.agiletv.shared.domain.bus.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteUserCommandHandler implements CommandHandler<DeleteUserCommand> {
  private final DeleteUser deleteUser;
  
  @Override
  public void handle(DeleteUserCommand command) {
    deleteUser.delete(command.getUsername());
  }
}
