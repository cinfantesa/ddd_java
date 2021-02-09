package com.agiletv.user.application.create_user;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.event.UserCreated;
import com.agiletv.user.domain.exception.EmailIsRequired;
import com.agiletv.user.domain.exception.NameIsRequired;
import com.agiletv.user.domain.exception.UserAlreadyExists;
import com.agiletv.user.domain.exception.UserNameIsRequired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CreateUserTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private EventBus eventBus;
  
  @Captor
  private ArgumentCaptor<List<DomainEvent>> domainEventsCaptor;
  
  private CreateUser createUser;
  
  @BeforeEach
  void setUp() {
    createUser = new CreateUser(userRepository, eventBus);
  }
  
  @Test
  void should_throw_exception_when_username_is_not_present() {
    assertThrows(UserNameIsRequired.class, () -> createUser.create(null, null, null, null, null));
  }
  
  @Test
  void should_throw_exception_when_name_is_not_present() {
    assertThrows(NameIsRequired.class, () -> createUser.create("fakeUsername", null, null, null, null));
  }
  
  @Test
  void should_throw_exception_when_enmail_is_not_present() {
    assertThrows(EmailIsRequired.class,
      () -> createUser.create("fakeUsername", new Name("test", "name"), null, null, null));
  }
  
  @Test
  void should_throw_exception_when_user_exists_with_given_username() {
    when(userRepository.exists("jbond")).thenReturn(true);
    
    assertThrows(UserAlreadyExists.class, () -> createUser.create("jbond", null, null, null, null));
  }
  
  @Test
  void should_save_user() {
    when(userRepository.exists("username")).thenReturn(false);
    createUser.create("username", new Name("test", "name"), "test@mail.com", MALE, "testUrl");
    
    User expectedUser = new User("username", new Name("test", "name"), "test@mail.com", MALE, "testUrl");
    verify(userRepository, times(1)).save(expectedUser);
    verifyNoMoreInteractions(userRepository);
  
    verify(eventBus, times(1)).publish(domainEventsCaptor.capture());
    List<UserCreated> expectedEvents = List.of(new UserCreated("username", expectedUser));
    assertThat(domainEventsCaptor.getValue().size()).isEqualTo(expectedEvents.size());
    assertThat(domainEventsCaptor.getValue().get(0))
      .isEqualToIgnoringGivenFields(expectedEvents.get(0), "id", "occurredOn");
  }
}
