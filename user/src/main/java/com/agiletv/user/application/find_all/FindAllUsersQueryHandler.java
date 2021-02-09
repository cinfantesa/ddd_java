package com.agiletv.user.application.find_all;

import com.agiletv.shared.domain.bus.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindAllUsersQueryHandler implements QueryHandler<FindAllUsersQuery, FindAllUsersResponse> {
  private final FindAllUsers findAllUsers;
  
  @Override
  public FindAllUsersResponse handle(FindAllUsersQuery query) {
    return findAllUsers.find(query.getPage(), query.getSize());
  }
}
