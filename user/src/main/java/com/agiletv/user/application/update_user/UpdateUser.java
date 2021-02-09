package com.agiletv.user.application.update_user;

import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.exception.UserNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateUser {
  private final UserRepository userRepository;
  private final EventBus eventBus;
  
  public void update(String username, Name name, String email, Gender gender, String pictureUrl) {
    User user = userRepository.findById(username)
      .orElseThrow(UserNotFound::new);
    
    user.updateInformation(name, email, gender, pictureUrl);
    userRepository.save(user);
    eventBus.publish(user.retrieveAllEvents());
  }
}
