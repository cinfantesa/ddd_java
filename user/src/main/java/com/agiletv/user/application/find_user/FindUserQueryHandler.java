package com.agiletv.user.application.find_user;

import com.agiletv.shared.domain.bus.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindUserQueryHandler implements QueryHandler<FindUserQuery, FindUserResponse> {
  private final FindUser findUser;
  
  @Override
  public FindUserResponse handle(FindUserQuery query) {
    return findUser.find(query.getUsername());
  }
}
