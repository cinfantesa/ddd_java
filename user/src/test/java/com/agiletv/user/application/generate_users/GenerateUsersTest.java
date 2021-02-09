package com.agiletv.user.application.generate_users;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserGenerator;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.event.UserCreated;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateUsersTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private UserGenerator userGenerator;
  @Mock
  private EventBus eventBus;
  
  @Captor
  private ArgumentCaptor<List<DomainEvent>> domainEventsCaptor;
  
  private GenerateUsers generateUsers;
  
  @BeforeEach
  void setUp() {
    generateUsers = new GenerateUsers(userRepository, userGenerator, eventBus);
  }
  
  @Test
  void should_save_generated_users() {
    User user = User.create("jbond", new Name("James", "Bond"), "james@bond.com", MALE, "url");
    when(userGenerator.generate(1)).thenReturn(List.of(user));
    
    generateUsers.generate(1);
    
    verify(userGenerator, times(1)).generate(1);
    verifyNoMoreInteractions(userGenerator);
    
    verify(userRepository, times(1)).saveAll(List.of(user));
    verifyNoMoreInteractions(userRepository);
  
    verify(eventBus, times(1)).publish(domainEventsCaptor.capture());
    List<DomainEvent> expectedEvents = List.of(new UserCreated("jbond", user));
    assertThat(domainEventsCaptor.getValue().size()).isEqualTo(expectedEvents.size());
    assertThat(domainEventsCaptor.getValue().get(0))
      .isEqualToIgnoringGivenFields(expectedEvents.get(0), "id", "occurredOn");
  }
}