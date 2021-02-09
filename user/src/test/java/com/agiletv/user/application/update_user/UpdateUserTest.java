package com.agiletv.user.application.update_user;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.event.UserUpdated;
import com.agiletv.user.domain.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private EventBus eventBus;
  
  @Captor
  private ArgumentCaptor<List<DomainEvent>> domainEventsCaptor;
  
  private UpdateUser updateUser;
  
  @BeforeEach
  void setUp() {
    updateUser = new UpdateUser(userRepository, eventBus);
  }
  
  @Test
  void should_throw_exception_when_user_not_exists() {
    when(userRepository.findById("wrong")).thenReturn(Optional.empty());
  
    assertThrows(UserNotFound.class, () -> updateUser.update("wrong", null, null, null, null));
    verify(userRepository, times(1)).findById("wrong");
    verifyNoMoreInteractions(userRepository);
  }
  
  @Test
  void should_update_user_information() {
    User user = new User("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url");
    when(userRepository.findById("jbond")).thenReturn(Optional.of(user));
    
    updateUser.update("jbond", new Name("James", "Bond"), "james.bond@mail.com", MALE, "anotherUrl");
    verify(userRepository, times(1)).findById("jbond");
  
    User expectedUSer = new User("jbond", new Name("James", "Bond"), "james.bond@mail.com", MALE, "anotherUrl");
    verify(userRepository, times(1)).save(expectedUSer);
  
    verify(eventBus, times(1)).publish(domainEventsCaptor.capture());
    List<UserUpdated> expectedEvents = List.of(new UserUpdated(expectedUSer.getUsername(), expectedUSer));
    assertThat(domainEventsCaptor.getValue().size()).isEqualTo(expectedEvents.size());
    assertThat(domainEventsCaptor.getValue().get(0))
      .isEqualToIgnoringGivenFields(expectedEvents.get(0), "id", "occurredOn");
  }
}