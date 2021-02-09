package com.agiletv.shared.domain.bus.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.UUID.randomUUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class DomainEvent {
  private static final DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm");
  private final String id;
  private final String name;
  private final String aggregateId;
  private final String aggregateName;
  private final LocalDateTime occurredOn;
  private final Object payload;
  
  public DomainEvent(String aggregateId, String aggregateName, Object payload) {
    this.aggregateId = aggregateId;
    this.aggregateName = aggregateName;
    this.payload = payload;
    
    this.id = randomUUID().toString();
    this.occurredOn = now();
    this.name = this.getClass().getName();
  }
}
