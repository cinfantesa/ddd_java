package com.agiletv.user.application.generate_users;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserGenerator;
import com.agiletv.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class GenerateUsers {
  private final UserRepository userRepository;
  private final UserGenerator userGenerator;
  private final EventBus eventBus;
  
  public void generate(int quantity) {
    List<User> generatedUsers = userGenerator.generate(quantity);
    
    userRepository.saveAll(generatedUsers);
  
    List<DomainEvent> events = generatedUsers.stream()
      .map(User::retrieveAllEvents)
      .flatMap(Collection::stream)
      .collect(toList());
    eventBus.publish(events);
  }
}
