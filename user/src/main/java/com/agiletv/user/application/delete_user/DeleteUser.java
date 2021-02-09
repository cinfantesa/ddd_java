package com.agiletv.user.application.delete_user;

import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.event.UserDeleted;
import com.agiletv.user.domain.exception.UserNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DeleteUser {
  private final UserRepository userRepository;
  private final EventBus eventBus;
  
  public void delete(String username) {
    ensureUserExists(username);
  
    userRepository.delete(username);
    
    UserDeleted event = new UserDeleted(username, User.class.getSimpleName());
    eventBus.publish(List.of(event));
  }
  
  private void ensureUserExists(String username) {
    if (!userRepository.exists(username)) {
      throw new UserNotFound();
    }
  }
}
