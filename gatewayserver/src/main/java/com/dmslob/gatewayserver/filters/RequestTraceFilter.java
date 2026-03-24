package com.dmslob.gatewayserver.filters;

import com.dmslob.gatewayserver.service.RequestFilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RequestTraceFilter implements GlobalFilter {

    private final RequestFilterService requestFilterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Filtering incoming request for {}", exchange.getRequest().getURI());
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (hasCorrelationId(requestHeaders)) {
            log.info("atlas-correlation-id is found: {}",
                    requestFilterService.getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = requestFilterService.setCorrelationId(exchange, correlationID);
            log.info("atlas-correlation-id is generated: {}", correlationID);
        }
        return chain.filter(exchange);
    }

    private boolean hasCorrelationId(HttpHeaders requestHeaders) {
        return requestFilterService.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}