package com.dmslob.reservationservice.service.client;

import com.dmslob.reservationservice.model.GuestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "guest-service", fallback =  GuestFallback.class)
public interface GuestFeignClient {

    @GetMapping(value = "/guests/{id}")
    GuestDto findById(@RequestHeader("atlas-correlation-id") String correlationId,
                      @PathVariable Long id);
}
