package com.agiletv.user.application.find_all;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllUsersTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private FindAllUsersResponseParser responseParser;
  
  private FindAllUsers findAllUsers;
  
  @BeforeEach
  void setUp() {
    findAllUsers = new FindAllUsers(userRepository, responseParser);
  }
  
  @Test
  void should_return_existing_users() {
    List<User> users = List.of(new User("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url"));
    when(userRepository.findAll(0, 5)).thenReturn(users);
  
    FindAllUsersResponse response = FindAllUsersResponse.builder()
      .content(List.of(UserResponse.builder()
        .username("jbond")
        .name("James")
        .firstSurname("Bond")
        .email("james@bond.com")
        .gender("MALE")
        .pictureUrl("url")
        .build()))
      .build();
    when(responseParser.parseToResponse(users)).thenReturn(response);
  
    FindAllUsersResponse actualResponse = findAllUsers.find(0, 5);
    verify(userRepository, times(1)).findAll(0, 5);
    verifyNoMoreInteractions(userRepository);
    verify(responseParser, times(1)).parseToResponse(users);
    assertThat(actualResponse).isEqualTo(response);
  }
}