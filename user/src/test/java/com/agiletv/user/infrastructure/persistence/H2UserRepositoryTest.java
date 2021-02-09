package com.agiletv.user.infrastructure.persistence;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class H2UserRepositoryTest {
  @Mock
  private JpaUserRepository jpaUserRepository;
  @Mock
  private UserEntityParser userEntityParser;
  
  private H2UserRepository h2UserRepository;
  
  @BeforeEach
  void setUp() {
    h2UserRepository = new H2UserRepository(jpaUserRepository, userEntityParser);
  }
  
  @Test
  void should_save_user() {
    User user = new User("username", new Name("test", "bond"), "test@mail.com", MALE, "thisIs.an.url");
  
    UserEntity expectedEntity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
  
    when(userEntityParser.toEntity(user)).thenReturn(expectedEntity);
    
    h2UserRepository.save(user);
    
    verify(userEntityParser, times(1)).toEntity(user);
    verifyNoMoreInteractions(userEntityParser);
    verify(jpaUserRepository, times(1)).save(expectedEntity);
    verifyNoMoreInteractions(jpaUserRepository);
  }
  
  @Test
  void should_return_true_when_existing_user_with_give_username() {
    when(jpaUserRepository.count(any(Specification.class))).thenReturn(1L);
  
    boolean exists = h2UserRepository.exists("jbond");
  
    assertThat(exists).isTrue();
    verify(jpaUserRepository, times(1)).count(any(Specification.class));
    verifyNoMoreInteractions(jpaUserRepository);
  }
  
  @Test
  void should_return_false_when_not_existing_user_with_given_username() {
    when(jpaUserRepository.count(any(Specification.class))).thenReturn(0L);
  
    boolean exists = h2UserRepository.exists("jbond");
  
    assertThat(exists).isFalse();
    verify(jpaUserRepository, times(1)).count(any(Specification.class));
    verifyNoMoreInteractions(jpaUserRepository);
  }
  
  @Test
  void should_delete_user_with_given_username() {
    h2UserRepository.delete("jbond");
    
    verify(jpaUserRepository, times(1)).deleteById("jbond");
    verifyNoMoreInteractions(jpaUserRepository);
  }
  
  @Test
  void should_return_optional_empty_when_user_not_found() {
    when(jpaUserRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
    
    h2UserRepository.findById("jbond");
    
    verify(jpaUserRepository, times(1)).findOne(any(Specification.class));
    verifyNoMoreInteractions(jpaUserRepository);
  }
  
  @Test
  void should_return_optional_when_user_exists() {
    UserEntity expectedEntity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
    User expectedUser = new User("username", new Name("test", "bond"), "test@mail.com", MALE, "thisIs.an.url");
    
    when(jpaUserRepository.findOne(any(Specification.class))).thenReturn(Optional.of(expectedEntity));
    when(userEntityParser.toDomain(expectedEntity)).thenReturn(expectedUser);
  
    Optional<User> actualUser = h2UserRepository.findById("username");
    
    verify(jpaUserRepository, times(1)).findOne(any(Specification.class));
    verifyNoMoreInteractions(jpaUserRepository);
    verify(userEntityParser, times(1)).toDomain(expectedEntity);
    assertThat(actualUser.isPresent()).isTrue();
    assertThat(actualUser.get()).isEqualTo(expectedUser);
  }
  
  @Test
  void should_return_all_existing_users() {
    UserEntity entity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
    
    when(jpaUserRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<>(List.of(entity)));

    User user = new User("username", new Name("test", "bond"), "test@mail.com", MALE, "thisIs.an.url");
    when(userEntityParser.toDomain(entity)).thenReturn(user);
    
    List<User> all = h2UserRepository.findAll(0, 5);
    
    verify(jpaUserRepository, times(1)).findAll(PageRequest.of(0, 5));
    verifyNoMoreInteractions(jpaUserRepository);
    verify(userEntityParser, times(1)).toDomain(entity);
    assertThat(all).isEqualTo(List.of(user));
  }
}