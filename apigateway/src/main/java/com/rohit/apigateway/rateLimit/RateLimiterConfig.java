package com.rohit.apigateway.rateLimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // Username from header (set by JWT filter)
            String username = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-Auth-User");

            if (username == null || username.isBlank()) {
                username = "anonymous";
            }

            // Client IP
            var address = exchange.getRequest().getRemoteAddress();
            String ip = address != null
                    ? address.getAddress().getHostAddress()
                    : "unknown";

            return Mono.just(username + ":" + ip);
        };
    }
}
