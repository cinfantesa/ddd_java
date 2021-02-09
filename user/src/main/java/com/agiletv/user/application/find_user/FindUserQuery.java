package com.agiletv.user.application.find_user;

import com.agiletv.shared.domain.bus.query.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class FindUserQuery implements Query {
  private final String username;
}
