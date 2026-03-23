package com.dmslob.reservationservice.service.client;

import com.dmslob.reservationservice.model.GuestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guest-service")
public interface GuestFeignClient {

    @GetMapping(value = "/guests/{id}")
    GuestDto findById(@PathVariable Long id);
}
