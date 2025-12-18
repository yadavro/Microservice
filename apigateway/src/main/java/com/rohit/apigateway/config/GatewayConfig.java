package com.rohit.apigateway.config;

import com.rohit.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final KeyResolver userKeyResolver;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                         KeyResolver userKeyResolver) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userKeyResolver = userKeyResolver;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(redisRateLimiter());
                                    config.setKeyResolver(userKeyResolver);
                                })
                                .circuitBreaker(c->{
                                    c.setName("userServiceCB");
                                    c.setFallbackUri("forward:/fallback/users");
                                })
                        )
                        .uri("http://localhost:8081"))
                .build();
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2);
    }


}
