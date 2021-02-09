package com.agiletv.shared.infrastructure.bus.query;

import com.agiletv.shared.domain.bus.query.Query;
import com.agiletv.shared.domain.bus.query.QueryHandler;
import com.agiletv.shared.domain.bus.query.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class QueryHandlerInspectorTest {
  static class TestQuery implements Query {}
  static class TestResponse implements Response {}
  
  @Test
  void should_retrieve_right_handle_inspector() {
    TestQuery testQuery = new TestQuery();
    TestResponse testResponse = new TestResponse();
    QueryHandler<TestQuery, TestResponse> handler = new QueryHandler<TestQuery, TestResponse>() {
      
      @Override
      public TestResponse handle(TestQuery query) {
        return testResponse;
      }
    };
    
    QueryHandlerInspector inspector = new QueryHandlerInspector(List.of(handler));
    QueryHandler<? extends Query, ? extends Response> actualHandler = inspector.searchHandlerFor(testQuery);
    
    assertThat(actualHandler).isEqualTo(handler);
  }
  
  @Test
  void should_return_null_when_handler_not_found() {
    TestQuery testQuery = new TestQuery();
    
    QueryHandlerInspector inspector = new QueryHandlerInspector(emptyList());
    QueryHandler<? extends Query, ? extends Response> actualHandler = inspector.searchHandlerFor(testQuery);
    
    assertThat(actualHandler).isNull();
  }
}