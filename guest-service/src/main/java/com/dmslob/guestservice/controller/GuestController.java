package com.dmslob.guestservice.controller;

import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/guests")
public class GuestController {
    private final GuestService guestService;

    @Operation(summary = "Get all Guests", description = "Get all Guests", method = "GET")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GuestDto> getAll() {
        log.info("Getting all Guests");
        return guestService.getAll();
    }

    @Operation(
            summary = "Get Guest by ID",
            description = "Get Guest by ID",
            parameters = {@Parameter(name = "id")},
            method = "GET")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GuestDto getById(@PathVariable(name = "id") long guestId) {
        log.info("Getting Guest by id {}", guestId);
        return guestService.getById(guestId);
    }

    @Operation(
            summary = "Get Guest by email address",
            description = "Get Guest by email address",
            parameters = {@Parameter(name = "emailAddress")},
            method = "GET")
    @GetMapping(params = "email")
    @ResponseStatus(HttpStatus.OK)
    public GuestDto getByEmail(@RequestParam(name = "email") String email) {
        log.info("Getting Guest by email {}", email);
        return guestService.getByEmail(email);
    }
}
