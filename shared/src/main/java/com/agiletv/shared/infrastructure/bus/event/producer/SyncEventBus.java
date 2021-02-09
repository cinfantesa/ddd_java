package com.agiletv.shared.infrastructure.bus.event.producer;

import com.agiletv.shared.domain.bus.event.DomainEvent;
import com.agiletv.shared.domain.bus.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SyncEventBus implements EventBus {
  private final ApplicationEventPublisher publisher;
  
  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(publisher::publishEvent);
  }
}
