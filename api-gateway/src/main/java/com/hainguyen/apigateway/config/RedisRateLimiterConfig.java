package com.hainguyen.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RedisRateLimiterConfig {

    @Bean
    public KeyResolver ipKeyResolver() {
        // IP = 121.122.1.1, COUNT = 10.
        return exchange ->  Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
