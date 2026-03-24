package com.dmslob.gatewayserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class RequestFilterService {
    private final String correlationIdHeader;

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(correlationIdHeader) != null) {
            List<String> headers = requestHeaders.get(correlationIdHeader);
            assert headers != null;
            return headers.stream().findFirst().orElse(null);
        } else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, correlationIdHeader, correlationId);
    }
}
