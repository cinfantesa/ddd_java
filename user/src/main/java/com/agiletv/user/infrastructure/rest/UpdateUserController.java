package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.update_user.UpdateUserCommand;
import com.agiletv.user.infrastructure.rest.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@AllArgsConstructor
public class UpdateUserController {
  private final CommandBus commandBus;
  
  @PutMapping(path = "/api/user/{username}")
  public ResponseEntity<Void> updateUser(
    @PathVariable("username") String username,
    @Valid @RequestBody UpdateUserRequest request) {
  
    UpdateUserCommand command = UpdateUserCommand.builder()
      .username(username)
      .name(request.getName())
      .firstSurname(request.getFirstSurname())
      .email(request.getEmail())
      .gender(request.getGender())
      .pictureUrl(request.getPictureUrl())
      .build();
    
    commandBus.dispatch(command);
    return noContent().build();
  }
}
