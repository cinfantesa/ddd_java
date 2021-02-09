package com.agiletv.shared.infrastructure.bus.command;

import com.agiletv.shared.domain.bus.command.Command;
import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.shared.domain.bus.command.CommandHandler;
import com.agiletv.shared.domain.bus.command.CommandHandlerNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class SyncCommandBus implements CommandBus {
  private final CommandHandlerInspector inspector;
  
  @Override
  public void dispatch(Command command) {
    CommandHandler handler = inspector.searchHandlerFor(command);
    ensureCommandHandlerIsNotNull(handler);
    
    handler.handle(command);
  }
  
  private void ensureCommandHandlerIsNotNull(CommandHandler handler) {
    if (isNull(handler)) {
      throw new CommandHandlerNotFound();
    }
  }
}
