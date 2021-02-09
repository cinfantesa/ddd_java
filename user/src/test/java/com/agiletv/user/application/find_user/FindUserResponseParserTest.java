package com.agiletv.user.application.find_user;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class FindUserResponseParserTest {
  private FindUserResponseParser findUserResponseParser;
  
  @BeforeEach
  void setUp() {
    findUserResponseParser = new FindUserResponseParser();
  }
  
  @Test
  void should_parse_to_domain() {
    User user = new User("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url");
  
    FindUserResponse response = findUserResponseParser.parseToResponse(user);
  
    FindUserResponse expectedResponse = FindUserResponse.builder()
      .username("jbond")
      .name("James")
      .firstSurname("Bond")
      .email("james@bond.com")
      .gender("MALE")
      .pictureUrl("url")
      .build();
  
    assertThat(response).isEqualTo(expectedResponse);
  }
}