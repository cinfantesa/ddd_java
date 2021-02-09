package com.agiletv.shared.domain.bus.query;

public interface ResponseParser<D, R extends Response> {
  R parseToResponse(D domain);
}
