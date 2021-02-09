package com.agiletv.shared.infrastructure.bus.command;

import com.agiletv.shared.domain.bus.command.Command;
import com.agiletv.shared.domain.bus.command.CommandHandler;
import com.agiletv.shared.domain.bus.command.CommandHandlerNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SyncCommandBusTest {
  @Mock
  private CommandHandlerInspector inspector;
  @Mock
  private CommandHandler handler;
  
  private SyncCommandBus commandBus;
  
  static class TestCommand implements Command {}
  
  @BeforeEach
  void setUp() {
    commandBus = new SyncCommandBus(inspector);
  }
  
  @Test
  void should_throw_exception_when_command_handler_not_found() {
    TestCommand command = new TestCommand();
    
    when(inspector.searchHandlerFor(command)).thenReturn(null);
    
    assertThrows(CommandHandlerNotFound.class, () -> commandBus.dispatch(command));
  }
  
  @Test
  void should_handle_command() {
    TestCommand command = new TestCommand();
    
    when(inspector.searchHandlerFor(command)).thenReturn(handler);
    commandBus.dispatch(command);
    
    verify(handler, times(1)).handle(command);
  }
}