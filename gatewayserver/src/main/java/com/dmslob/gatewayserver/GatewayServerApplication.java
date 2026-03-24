package com.dmslob.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
                        .uri("lb://GUEST-SERVICE"))
                .route(p -> p
                        .path("/rooms/**")
                        .uri("lb://ROOM-SERVICE"))
                .route(p -> p
                        .path("/reservations/**")
                        .uri("lb://RESERVATION-SERVICE"))
                .build();
    }
}
