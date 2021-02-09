package com.agiletv.user;

import com.agiletv.shared.infrastructure.bus.command.CommandBusConfiguration;
import com.agiletv.shared.infrastructure.bus.event.producer.EventBusConfiguration;
import com.agiletv.shared.infrastructure.bus.query.QueryBusConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@Import({EventBusConfiguration.class, CommandBusConfiguration.class, QueryBusConfiguration.class})
public class UserConfiguration {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
