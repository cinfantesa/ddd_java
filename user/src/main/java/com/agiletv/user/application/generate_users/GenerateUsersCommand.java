package com.agiletv.user.application.generate_users;

import com.agiletv.shared.domain.bus.command.Command;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class GenerateUsersCommand implements Command {
  private final int quantity;
}
