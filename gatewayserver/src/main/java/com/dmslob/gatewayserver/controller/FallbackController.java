package com.dmslob.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @GetMapping("/guests")
    public String guestsServiceFallback() {
        return "Guest Service is unavailable. Please try again later.";
    }

    @GetMapping("/reservations")
    public String reservationServiceFallback() {
        return "Reservation Service is unavailable. Please try again later.";
    }

    @GetMapping("/rooms")
    public String roomServiceFallback() {
        return "Room Service is unavailable. Please try again later.";
    }
}
