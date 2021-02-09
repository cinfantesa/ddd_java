package com.agiletv.user.application.update_user;

import com.agiletv.shared.domain.bus.command.Command;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class UpdateUserCommand implements Command {
  private final String username;
  private final String name;
  private final String firstSurname;
  private final String email;
  private final String gender;
  private final String pictureUrl;
}
