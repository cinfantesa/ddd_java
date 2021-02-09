package com.agiletv.user.application.generate_users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GenerateUsersCommandHandlerTest {
  @Mock
  private GenerateUsers generateUsers;
  
  private GenerateUsersCommandHandler commandHandler;
  
  @BeforeEach
  void setUp() {
    commandHandler = new GenerateUsersCommandHandler(generateUsers);
  }
  
  @Test
  void should_call_application_service() {
    GenerateUsersCommand command = GenerateUsersCommand.builder()
      .quantity(1)
      .build();
    
    commandHandler.handle(command);
  
    verify(generateUsers, times(1)).generate(1);
  }
}