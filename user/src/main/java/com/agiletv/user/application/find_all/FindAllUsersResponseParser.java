package com.agiletv.user.application.find_all;

import com.agiletv.shared.domain.bus.query.ResponseParser;
import com.agiletv.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FindAllUsersResponseParser implements ResponseParser<List<User>, FindAllUsersResponse> {
  
  @Override
  public FindAllUsersResponse parseToResponse(List<User> domain) {
    List<UserResponse> content = domain.stream()
      .map(this::parseUserToResponse)
      .collect(toList());
    
    return FindAllUsersResponse.builder()
      .content(content)
      .build();
  }
  
  private UserResponse parseUserToResponse(User user) {
    return UserResponse.builder()
      .username(user.getUsername())
      .name(user.getName().getName())
      .firstSurname(user.getName().getFirstSurname())
      .email(user.getEmail())
      .gender(user.getGender().name())
      .pictureUrl(user.getPictureUrl())
      .build();
  }
}
