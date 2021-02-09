package com.agiletv.shared.domain;

import com.agiletv.shared.domain.bus.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public abstract class AggregateRoot {
  private List<DomainEvent> domainEvents = new ArrayList<>();
  
  final public List<DomainEvent> retrieveAllEvents() {
    List<DomainEvent> events = domainEvents;
    domainEvents = emptyList();
    
    return events;
  }
  
  final protected void registerEvent(DomainEvent event) {
    domainEvents.add(event);
  }
}
