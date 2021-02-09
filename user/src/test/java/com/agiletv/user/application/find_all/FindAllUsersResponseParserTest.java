package com.agiletv.user.application.find_all;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class FindAllUsersResponseParserTest {
  private FindAllUsersResponseParser responseParser;
  
  @BeforeEach
  void setUp() {
    responseParser = new FindAllUsersResponseParser();
  }
  
  @Test
  void should_parse_response() {
    List<User> users = List.of(new User("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url"));
  
    FindAllUsersResponse response = responseParser.parseToResponse(users);
  
    FindAllUsersResponse expectedResponse = FindAllUsersResponse.builder()
      .content(List.of(UserResponse.builder()
        .username("jbond")
        .name("James")
        .firstSurname("Bond")
        .email("james@bond.com")
        .gender("MALE")
        .pictureUrl("url")
        .build()))
      .build();
    assertThat(response).isEqualTo(expectedResponse);
  }
}