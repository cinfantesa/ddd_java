package com.agiletv.user.application.create_user;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.exception.GenderNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.agiletv.user.domain.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {
  @Mock
  private CreateUser createUser;
  private CreateUserCommandHandler commandHandler;
  
  @BeforeEach
  void setUp() {
    commandHandler = new CreateUserCommandHandler(createUser);
  }
  
  @Test
  void should_throw_exception_when_gender_is_not_valid() {
    CreateUserCommand commnad = CreateUserCommand.builder()
      .username("jbond")
      .name("James")
      .firstSurname("Bond")
      .gender("WRONG")
      .email("james.bond@mail.com")
      .pictureUrl("testUrl")
      .build();
  
    assertThrows(GenderNotFound.class, () -> commandHandler.handle(commnad));
  }
  
  @Test
  void should_call_create_user_application_service() {
    CreateUserCommand commnad = CreateUserCommand.builder()
      .username("jbond")
      .name("James")
      .firstSurname("Bond")
      .gender("MALE")
      .email("james.bond@mail.com")
      .pictureUrl("testUrl")
      .build();
    
    commandHandler.handle(commnad);
  
    verify(createUser, times(1)).create("jbond", new Name("James", "Bond"), "james.bond@mail.com", MALE, "testUrl");
  }
}