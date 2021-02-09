package com.agiletv.user.application.find_user;

import com.agiletv.shared.domain.bus.query.ResponseParser;
import com.agiletv.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class FindUserResponseParser implements ResponseParser<User, FindUserResponse> {
  
  @Override
  public FindUserResponse parseToResponse(User domain) {
    return FindUserResponse.builder()
      .username(domain.getUsername())
      .name(domain.getName().getName())
      .firstSurname(domain.getName().getFirstSurname())
      .email(domain.getEmail())
      .gender(domain.getGender().name())
      .pictureUrl(domain.getPictureUrl())
      .build();
  }
}
