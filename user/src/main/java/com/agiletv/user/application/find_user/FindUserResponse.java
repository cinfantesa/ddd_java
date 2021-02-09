package com.agiletv.user.application.find_user;

import com.agiletv.shared.domain.bus.query.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class FindUserResponse implements Response {
  private final String username;
  private final String name;
  private final String firstSurname;
  private final String email;
  private final String gender;
  private final String pictureUrl;
}
