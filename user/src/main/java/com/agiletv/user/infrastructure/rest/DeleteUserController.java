package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.delete_user.DeleteUserCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
public class DeleteUserController {
  private final CommandBus commandBus;
  
  @DeleteMapping(path = "/api/user/{username}")
  public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
    DeleteUserCommand command = DeleteUserCommand.builder()
      .username(username)
      .build();
  
    commandBus.dispatch(command);
    return ok().build();
  }
}
