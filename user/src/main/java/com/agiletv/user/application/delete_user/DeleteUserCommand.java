package com.agiletv.user.application.delete_user;

import com.agiletv.shared.domain.bus.command.Command;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class DeleteUserCommand implements Command {
  private final String username;
}
