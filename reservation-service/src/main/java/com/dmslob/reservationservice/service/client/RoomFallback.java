package com.dmslob.reservationservice.service.client;

import com.dmslob.reservationservice.model.RoomDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomFallback implements RoomFeignClient {

    @Override
    public RoomDto findById(Long id) {
        log.error("Failed to get room with id {}.", id);
        return null;
    }
}
