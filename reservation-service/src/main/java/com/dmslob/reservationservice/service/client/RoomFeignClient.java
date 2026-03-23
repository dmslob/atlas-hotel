package com.dmslob.reservationservice.service.client;

import com.dmslob.reservationservice.model.RoomDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("room-service")
public interface RoomFeignClient {

    @GetMapping(value = "/rooms/{id}")
    RoomDto findById(@PathVariable Long id);
}
