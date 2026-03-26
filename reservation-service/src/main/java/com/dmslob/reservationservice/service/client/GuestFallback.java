package com.dmslob.reservationservice.service.client;

import com.dmslob.reservationservice.model.GuestDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuestFallback implements GuestFeignClient {
    @Override
    public GuestDto findById(String correlationId, Long id) {
        log.error("Failed to get Guest with id {}.", id);
        return null;
    }
}
