package com.agiletv.shared.domain.bus.query;

public interface QueryBus {
  Response ask(Query query);
}
