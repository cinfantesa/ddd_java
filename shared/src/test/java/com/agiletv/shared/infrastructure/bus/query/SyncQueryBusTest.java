package com.agiletv.shared.infrastructure.bus.query;

import com.agiletv.shared.domain.bus.query.Query;
import com.agiletv.shared.domain.bus.query.QueryHandler;
import com.agiletv.shared.domain.bus.query.QueryHandlerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SyncQueryBusTest {
  @Mock
  private QueryHandlerInspector inspector;
  @Mock
  private QueryHandler handler;
  
  private SyncQueryBus queryBus;
  
  static class TestQuery implements Query {}
  
  @BeforeEach
  void setUp() {
    queryBus = new SyncQueryBus(inspector);
  }
  
  @Test
  void should_throw_exception_when_command_handler_not_found() {
    TestQuery testQuery = new TestQuery();
    
    when(inspector.searchHandlerFor(testQuery)).thenReturn(null);
    
    assertThrows(QueryHandlerNotFoundException.class, () -> queryBus.ask(testQuery));
  }
  
  @Test
  void should_handle_command() {
    TestQuery testQuery = new TestQuery();
    
    when(inspector.searchHandlerFor(testQuery)).thenReturn(handler);
    queryBus.ask(testQuery);
    
    verify(handler, times(1)).handle(testQuery);
  }
}