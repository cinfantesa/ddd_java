package com.agiletv.user.application.find_all;

import com.agiletv.shared.domain.bus.query.Response;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class FindAllUsersResponse implements Response {
  private final List<UserResponse> content;
}

@Getter
@Builder
@ToString
@EqualsAndHashCode
class UserResponse {
  private final String username;
  private final String name;
  private final String firstSurname;
  private final String email;
  private final String gender;
  private final String pictureUrl;
}
