package com.agiletv.user.application.delete_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserCommandHandlerTest {
  @Mock
  private DeleteUser deleteUser;
  private DeleteUserCommandHandler deleteUserCommandHandler;
  
  @BeforeEach
  void setUp() {
    deleteUserCommandHandler = new DeleteUserCommandHandler(deleteUser);
  }
  
  @Test
  void should_call_delete_user_application_service() {
    DeleteUserCommand command = DeleteUserCommand.builder()
      .username("jbond")
      .build();
    
    deleteUserCommandHandler.handle(command);
  
    verify(deleteUser, Mockito.times(1)).delete("jbond");
  }
}