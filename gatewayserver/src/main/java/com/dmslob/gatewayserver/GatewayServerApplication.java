package com.dmslob.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/guests/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("guestsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/guests"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://GUEST-SERVICE")
                )
                .route(p -> p
                        .path("/rooms/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("roomsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/rooms"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://ROOM-SERVICE"))
                .route(p -> p
                        .path("/reservations/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                //.retry(retryConfig -> retryConfig.setRetries(3)
                                //        .setMethods(HttpMethod.GET)
                                //        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
                                .circuitBreaker(config -> config.setName("reservationsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/reservations"))
                                .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://RESERVATION-SERVICE"))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(3)).build())
                .build());
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        int replenishRate = 1; // how many tokens per second in token-bucket algorithm.
        int burstCapacity = 1; // how many tokens the bucket can hold in token-bucket algorithm.
        int defaultRequestedTokens = 1; // how many tokens are requested per request
        return new RedisRateLimiter(replenishRate, burstCapacity, defaultRequestedTokens);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange
                        .getRequest().getHeaders().getFirst("user"))
                .defaultIfEmpty("anonymous");
    }
}
