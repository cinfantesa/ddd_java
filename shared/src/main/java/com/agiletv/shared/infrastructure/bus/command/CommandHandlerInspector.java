package com.agiletv.shared.infrastructure.bus.command;

import com.agiletv.shared.domain.bus.command.Command;
import com.agiletv.shared.domain.bus.command.CommandHandler;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CommandHandlerInspector {
  private final HashMap<Class<? extends Command>, CommandHandler<? extends Command>> map = new HashMap<>();
  
  public CommandHandlerInspector(List<CommandHandler<? extends Command>> handlers) {
    handlers.forEach(handler -> {
      ResolvableType type = ResolvableType.forClass(handler.getClass()).as(CommandHandler.class);
      Class<? extends Command> command = (Class<? extends Command>) type.getGeneric(0).resolve();
      map.put(command, handler);
    });
  }
  
  public CommandHandler<? extends Command> searchHandlerFor(Command command) {
    return map.get(command.getClass());
  }
}
