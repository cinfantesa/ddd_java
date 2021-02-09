package com.agiletv.shared.infrastructure.bus.command;

import com.agiletv.shared.domain.bus.command.Command;
import com.agiletv.shared.domain.bus.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class CommandHandlerInspectorTest {
  static class TestCommand implements Command {}
  
  @Test
  void should_retrieve_right_handle_inspector() {
    Command testCommand = new TestCommand();
    CommandHandler<TestCommand> handler = new CommandHandler<TestCommand>() {
      @Override
      public void handle(TestCommand command) {
        System.out.println("ok");
      }
    };
    
    CommandHandlerInspector inspector = new CommandHandlerInspector(List.of(handler));
    CommandHandler<? extends Command> actualHandler = inspector.searchHandlerFor(testCommand);
    
    assertThat(actualHandler).isEqualTo(handler);
  }
  
  @Test
  void should_return_null_when_handler_not_found() {
    Command testCommand = new TestCommand();
    
    CommandHandlerInspector inspector = new CommandHandlerInspector(emptyList());
    CommandHandler<? extends Command> actualHandler = inspector.searchHandlerFor(testCommand);
    
    assertThat(actualHandler).isNull();
  }
}