package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.generate_users.GenerateUsersCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
public class GenerateUsersController {
  private final CommandBus commandBus;
  
  @GetMapping(path = "/api/user/generate/{number}")
  public ResponseEntity<Void> generateUsers(@PathVariable("number") int quantity) {
    GenerateUsersCommand command = GenerateUsersCommand.builder()
      .quantity(quantity)
      .build();
    
    commandBus.dispatch(command);
    return ok().build();
  }
}
