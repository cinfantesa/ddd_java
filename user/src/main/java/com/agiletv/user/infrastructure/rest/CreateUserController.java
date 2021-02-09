package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.create_user.CreateUserCommand;
import com.agiletv.user.infrastructure.rest.request.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.ResponseEntity.created;

@RestController
@AllArgsConstructor
public class CreateUserController {
  private final CommandBus commandBus;
  
  @PostMapping(path = "/api/user")
  public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) throws URISyntaxException {
  
    CreateUserCommand command = CreateUserCommand.builder()
      .username(request.getUsername())
      .name(request.getName())
      .firstSurname(request.getFirstSurname())
      .email(request.getEmail())
      .gender(request.getGender())
      .pictureUrl(request.getPictureUrl())
      .build();
    
    commandBus.dispatch(command);
    return created(new URI("/users/" + request.getUsername())).build();
  }
}
