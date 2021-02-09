package com.agiletv.user.application.find_all;

import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FindAllUsers {
  private final UserRepository userRepository;
  private final FindAllUsersResponseParser responseParser;
  
  public FindAllUsersResponse find(int page, int size) {
    List<User> all = userRepository.findAll(page, size);
  
    return responseParser.parseToResponse(all);
  }
}
