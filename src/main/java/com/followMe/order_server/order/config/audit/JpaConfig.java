package com.followMe.order_server.order.config.audit;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaConfig {

  @Bean
  public AuditorAware<UUID> auditorAware() {
    return () -> Optional.ofNullable(CurrentUserHolder.get()).map(UUID::fromString);
  }
}
