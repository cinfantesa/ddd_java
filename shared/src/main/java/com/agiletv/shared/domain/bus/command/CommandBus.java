package com.agiletv.shared.domain.bus.command;

public interface CommandBus {
  void dispatch(Command command);
}
