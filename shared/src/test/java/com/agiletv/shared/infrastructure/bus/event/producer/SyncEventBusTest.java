package com.agiletv.shared.infrastructure.bus.event.producer;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SyncEventBusTest {
  @Mock
  private ApplicationEventPublisher eventPublisher;
  
  private SyncEventBus syncEventBus;
  
  @Captor
  private ArgumentCaptor<List<DomainEvent>> domainEventsCaptor;
  
  static class TestEvent extends DomainEvent {
    public TestEvent(String aggregateId, String aggregateName, Object payload) {
      super(aggregateId, aggregateName, payload);
    }
  }
  
  @BeforeEach
  void setUp() {
    syncEventBus = new SyncEventBus(eventPublisher);
  }
  
  @Test
  void should_emit_all_events() {
    List<DomainEvent> events = List.of(
      new TestEvent("1", "Name1", null),
      new TestEvent("2", "Name2", null)
    );
    
    syncEventBus.publish(events);
    
    verify(eventPublisher, times(2)).publishEvent(domainEventsCaptor.capture());
    assertThat(((DomainEvent) domainEventsCaptor.getAllValues().get(0))).isEqualTo(events.get(0));
    assertThat(((DomainEvent)domainEventsCaptor.getAllValues().get(1))).isEqualTo(events.get(1));
  }
}