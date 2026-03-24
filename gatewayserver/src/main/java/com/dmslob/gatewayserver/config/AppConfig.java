package com.dmslob.gatewayserver.config;

import com.dmslob.gatewayserver.filters.RequestTraceFilter;
import com.dmslob.gatewayserver.service.RequestFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class AppConfig {
    public static final String CORRELATION_ID = "atlas-correlation-id";

    @Bean
    public RequestFilterService requestFilterService() {
        return new RequestFilterService(CORRELATION_ID);
    }

    @Order(1)
    @Bean
    public RequestTraceFilter requestTraceFilter(RequestFilterService requestFilterService) {
        return new RequestTraceFilter(requestFilterService);
    }

    @Bean
    public GlobalFilter postGlobalFilter(RequestFilterService requestFilterService) {
        return (exchange, chain) ->
                chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = requestFilterService.getCorrelationId(requestHeaders);
                    log.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                    exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
                }));
    }
}