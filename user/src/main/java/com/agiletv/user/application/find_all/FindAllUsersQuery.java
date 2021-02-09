package com.agiletv.user.application.find_all;

import com.agiletv.shared.domain.bus.query.Query;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class FindAllUsersQuery implements Query {
  private final int page;
  private final int size;
}
