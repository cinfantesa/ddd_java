package com.agiletv.shared.domain.bus.command;

public interface CommandHandler<C extends Command> {
  void handle(C command);
}
