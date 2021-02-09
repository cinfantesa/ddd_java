package com.agiletv.user.infrastructure.persistence;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.agiletv.user.domain.Gender.MALE;
import static com.agiletv.user.infrastructure.persistence.UserEntitySpecifications.username;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest
@Tag("IntegrationTest")
public class H2UserRepositoryIntegrationTest {
  @Autowired
  private JpaUserRepository jpaUserRepository;
  
  @Test
  void should_save_user() {
    UserEntity entity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
  
    UserEntity savedEntity = jpaUserRepository.save(entity);
  
    UserEntity expectedEntity = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender(MALE.name())
      .pictureUrl("thisIs.an.url")
      .build();
    assertThat(savedEntity).isEqualTo(expectedEntity);
    jpaUserRepository.delete(entity);
  }
  
  @Test
  void should_return_true_when_existing_user_with_given_username() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
    
    jpaUserRepository.save(user);
  
    long found = jpaUserRepository.count(where(username("username")));
    
    assertThat(found).isEqualTo(1L);
    jpaUserRepository.delete(user);
  }
  
  @Test
  void should_return_false_when_not_existing_user_with_given_username() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
    
    jpaUserRepository.save(user);
    
    long found = jpaUserRepository.count(where(username("another")));
    
    assertThat(found).isEqualTo(0L);
    jpaUserRepository.delete(user);
  }
  
  @Test
  void should_delete_user_with_given_username() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
  
    jpaUserRepository.save(user);
    
    long found = jpaUserRepository.count(where(username("username")));
    assertThat(found).isEqualTo(1L);
    
    jpaUserRepository.deleteById("username");
  
    found = jpaUserRepository.count(where(username("username")));
    assertThat(found).isEqualTo(0L);
  }
  
  @Test
  void should_return_optional_empty_when_user_not_exists() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
  
    jpaUserRepository.save(user);
  
    Optional<UserEntity> found = jpaUserRepository.findOne(where(username("wrong")));
  
    assertThat(found.isEmpty()).isTrue();
    jpaUserRepository.delete(user);
  }
  
  @Test
  void should_return_optiona_when_user_exists() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
    
    jpaUserRepository.save(user);
    
    Optional<UserEntity> found = jpaUserRepository.findOne(where(username("username")));
    
    assertThat(found.isPresent()).isTrue();
    assertThat(found.get()).isEqualTo(user);
    jpaUserRepository.delete(user);
  }
  
  @Test
  void should_return_existing_users() {
    UserEntity user = UserEntity.builder()
      .username("username")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .build();
  
    jpaUserRepository.save(user);
  
    List<UserEntity> all = jpaUserRepository.findAll();
    
    assertThat(all).isEqualTo(List.of(user));
    jpaUserRepository.delete(user);
  }
}
