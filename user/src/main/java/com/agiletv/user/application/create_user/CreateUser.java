package com.agiletv.user.application.create_user;

import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.exception.UserAlreadyExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUser {
  private final UserRepository userRepository;
  private final EventBus eventBus;
  
  public void create(String username, Name name, String email, Gender gender, String pictureUrl) {
    ensureUserDoesNotExists(username);
    User user = User.create(username, name, email, gender, pictureUrl);
    
    userRepository.save(user);
    eventBus.publish(user.retrieveAllEvents());
  }
  
  private void ensureUserDoesNotExists(String username) {
    if (userRepository.exists(username)) {
      throw new UserAlreadyExists();
    }
  }
}
