package com.dmslob.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
                                        .setFallbackUri("forward:/fallback/guests")))
                        .uri("lb://GUEST-SERVICE")
                )
                .route(p -> p
                        .path("/rooms/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("roomsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/rooms")))
                        .uri("lb://ROOM-SERVICE"))
                .route(p -> p
                        .path("/reservations/**")
                        .filters(f -> f
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                //.retry(retryConfig -> retryConfig.setRetries(3)
                                //        .setMethods(HttpMethod.GET)
                                //        .setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
                                .circuitBreaker(config -> config.setName("reservationsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/reservations")))
                        .uri("lb://RESERVATION-SERVICE"))
                .build();
    }
}
