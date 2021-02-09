package com.agiletv.user.application.find_user;

import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.exception.UserNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindUser {
  private final UserRepository userRepository;
  private final FindUserResponseParser responseParser;
  
  public FindUserResponse find(String username) {
    User user = userRepository.findById(username)
      .orElseThrow(UserNotFound::new);
    
    return responseParser.parseToResponse(user);
  }
}
