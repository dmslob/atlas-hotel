package com.dmslob.roomservice.controller;

import com.dmslob.roomservice.model.RoomDto;
import com.dmslob.roomservice.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Room details",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE Room details"
)
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/rooms")
public class RoomController {

    private final RoomService roomService;

    @Operation(
            summary = "Get All Rooms",
            description = "REST API to get all rooms or filter by room number",
            parameters = {@Parameter(name = "roomNumber",
                    description = "Optional query parameter to filter rooms by room number")},
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping
    public List<RoomDto> getByNumberOrAll(@RequestParam(name = "roomNumber", required = false) String roomNumber) {
        if (StringUtils.isNotEmpty(roomNumber)) {
            log.info("Get Room by room number: {}", roomNumber);
            return List.of(roomService.getByRoomNumber(roomNumber));
        }
        log.info("Get all Rooms");
        return roomService.getAll();
    }

    @Operation(
            summary = "Get Room by ID",
            description = "Gets a single room based on its unique id",
            parameters = {@Parameter(name = "id",
                    description = "Path variable representing the unique ID of the room to retrieve")},
            method = "GET"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error"
            )
    })
    @GetMapping(value = "/{id}")
    public RoomDto getById(@PathVariable long id) {
        log.info("Get Room by Id {}", id);
        return roomService.getById(id);
    }
}
