package com.agiletv.user.application.find_user;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private FindUserResponseParser responseParser;
  
  private FindUser findUser;
  
  @BeforeEach
  void setUp() {
    findUser = new FindUser(userRepository, responseParser);
  }
  
  @Test
  void should_throw_exception_when_user_not_found() {
    when(userRepository.findById("jbond")).thenReturn(Optional.empty());
  
    assertThrows(UserNotFound.class, () -> findUser.find("jbond"));
    verify(userRepository, times(1)).findById("jbond");
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(responseParser);
  }
  
  @Test
  void should_return_response_with_user() {
    User user = new User("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url");
    when(userRepository.findById("jbond")).thenReturn(Optional.of(user));
    FindUserResponse response = FindUserResponse.builder()
      .username("jbond")
      .name("James")
      .firstSurname("Bond")
      .email("james@bond.com")
      .gender("MALE")
      .pictureUrl("url")
      .build();
    when(responseParser.parseToResponse(user)).thenReturn(response);
  
    FindUserResponse actualResponse = findUser.find("jbond");
    verify(userRepository, times(1)).findById("jbond");
    verifyNoMoreInteractions(userRepository);
    verify(responseParser, times(1)).parseToResponse(user);
    assertThat(actualResponse).isEqualTo(response);
  }
}