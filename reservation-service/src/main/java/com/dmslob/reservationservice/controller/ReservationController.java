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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Create a new reservation",
            description = "Create a new reservation",
            method = "POST")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody ReservationDto dto) {
        log.info("Creating new reservation for guest id {} and room id {}",
                dto.getGuestId(), dto.getRoomId());
        reservationService.create(dto);
    }

    @Operation(
            summary = "Update an existing reservation",
            description = "Update an existing reservation",
            parameters = {@Parameter(name = "id")},
            method = "PUT")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request"),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @PutMapping(value = "/{id}")
    public ReservationDto update(@PathVariable Long id, @RequestBody ReservationDto dto) {
        log.info("Updating reservation with id {}", id);
        return reservationService.update(id, dto);
    }

    @Operation(
            summary = "Delete a reservation by ID",
            description = "Delete a reservation by ID",
            parameters = {@Parameter(name = "id")},
            method = "DELETE")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status No Content"),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error")
    })
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting reservation with id {}", id);
        reservationService.deleteById(id);
    }
}
