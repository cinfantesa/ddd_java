package com.agiletv.shared.infrastructure.bus.query;

import com.agiletv.shared.domain.bus.query.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class SyncQueryBus implements QueryBus {
  private final QueryHandlerInspector inspector;
  
  @Override
  public Response ask(Query query) {
    QueryHandler handler = inspector.searchHandlerFor(query);
    ensureQueryHandlerIsNotNull(handler);
    
    return handler.handle(query);
  }
  
  private void ensureQueryHandlerIsNotNull(QueryHandler handler) {
    if (isNull(handler)) {
      throw new QueryHandlerNotFoundException();
    }
  }
}
