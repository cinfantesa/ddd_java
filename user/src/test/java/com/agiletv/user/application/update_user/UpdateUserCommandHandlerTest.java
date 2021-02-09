package com.agiletv.user.application.update_user;

import com.agiletv.user.domain.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.agiletv.user.domain.Gender.MALE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserCommandHandlerTest {
  @Mock
  private UpdateUser updateUser;
  
  private UpdateUserCommandHandler commandHandler;
  
  @BeforeEach
  void setUp() {
    commandHandler = new UpdateUserCommandHandler(updateUser);
  }
  
  @Test
  void should_call_update_user_application_service() {
    UpdateUserCommand commnad = UpdateUserCommand.builder()
      .username("jbond")
      .name("James")
      .firstSurname("Bond")
      .gender("MALE")
      .email("james.bond@mail.com")
      .pictureUrl("testUrl")
      .build();
    
    commandHandler.handle(commnad);
    
    verify(updateUser, times(1)).update("jbond", new Name("James", "Bond"), "james.bond@mail.com", MALE, "testUrl");
  }
}