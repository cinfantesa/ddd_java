package com.agiletv.shared.infrastructure.bus.event.producer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.agiletv.shared.infrastructure.bus.event.producer" })
public class EventBusConfiguration {
}
