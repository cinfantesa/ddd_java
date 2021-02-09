package com.agiletv.shared.infrastructure.bus.query;

import com.agiletv.shared.domain.bus.query.Query;
import com.agiletv.shared.domain.bus.query.QueryHandler;
import com.agiletv.shared.domain.bus.query.Response;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class QueryHandlerInspector {
  private final HashMap<Class<? extends Query>, QueryHandler<? extends Query, ? extends Response>> map = new HashMap<>();
  
  public QueryHandlerInspector(List<QueryHandler<? extends Query, ? extends Response>> handlers) {
    handlers.forEach(handler -> {
      ResolvableType type = ResolvableType.forClass(handler.getClass()).as(QueryHandler.class);
      Class<? extends Query> query = (Class<? extends Query>) type.getGeneric(0).resolve();
      map.put(query, handler);
    });
  }
  
  public QueryHandler<? extends Query, ? extends Response> searchHandlerFor(Query query) {
    return map.get(query.getClass());
  }
}
