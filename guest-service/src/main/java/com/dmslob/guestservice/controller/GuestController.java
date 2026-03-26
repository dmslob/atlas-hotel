package com.dmslob.guestservice.controller;

import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Guest details",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE guest details"
)
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
    public List<GuestDto> getAll(@RequestHeader("atlas-correlation-id") String correlationId) {
        log.info("Getting all Guests with correlation id {}", correlationId);
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
                    responseCode = "404",
                    description = "Guest is not found"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @GetMapping(value = "/{id}")
    public GuestDto getById(
            @RequestHeader("atlas-correlation-id") String correlationId,
            @PathVariable(name = "id") Long guestId) {
        log.info("Getting Guest by id {} with correlation id {}", guestId, correlationId);
        return guestService.getById(guestId);
    }

    @Operation(
            summary = "Get Guest by email address",
            description = "Get Guest by email address",
            parameters = {@Parameter(name = "emailAddress")},
            method = "GET")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Guest is not found"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @GetMapping(params = "email")
    public GuestDto getByEmail(@RequestParam(name = "email") String email) {
        log.info("Getting Guest by email {}", email);
        return guestService.getByEmail(email);
    }

    @Operation(
            summary = "Create a new Guest",
            description = "Create a new Guest",
            method = "POST")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody GuestDto guestDto) {
        log.info("Creating Guest with email {}", guestDto.getEmail());
        guestService.create(guestDto);
    }
}
