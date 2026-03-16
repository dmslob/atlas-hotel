package com.dmslob.reservationservice.controller;

import com.dmslob.reservationservice.model.ReservationDto;
import com.dmslob.reservationservice.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Reservation details",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE reservation details"
)
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(
            summary = "Get all reservations",
            description = "Get all reservations",
            method = "GET")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @GetMapping
    public List<ReservationDto> getAll() {
        log.info("Getting all Reservations");
        return reservationService.getAll();
    }

    @Operation(
            summary = "Get Reservation by ID",
            description = "Get Reservation by ID",
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
    public ReservationDto getById(@PathVariable Long id) {
        log.info("Getting Reservation by id {}", id);
        return reservationService.getById(id);
    }
}
