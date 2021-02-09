package com.agiletv.user.infrastructure.persistence;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;

class UserEntityParserTest {
  private UserEntityParser userEntityParser;
  
  @BeforeEach
  void setUp() {
    userEntityParser = new UserEntityParser();
  }
  
  @Test
  void should_parse_to_domain() {
    UserEntity entity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
  
    User domain = userEntityParser.toDomain(entity);
    User expectedDomain = new User("username", new Name("test", "bond"), "test@mail.com", MALE, "thisIs.an.url");
  
    assertThat(domain).isEqualTo(expectedDomain);
  }
  
  @Test
  void should_parse_to_entity() {
    User domain = new User("username", new Name("test", "bond"), "test@mail.com", MALE, "thisIs.an.url");
  
    UserEntity entity = userEntityParser.toEntity(domain);
  
    UserEntity expectedEntity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
    
    assertThat(entity).isEqualTo(expectedEntity);
  }
}