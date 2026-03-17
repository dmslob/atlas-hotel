package com.dmslob.roomservice.service;

import com.dmslob.roomservice.model.RoomDto;

import java.util.List;

/**
 * Service for Room
 */
public interface RoomService {
    /**
     * Get Room by id
     *
     * @param roomId Room id
     * @return Room
     */
    RoomDto getById(Long roomId);

    /**
     * Get Room by room number
     *
     * @param roomNumber room number of Room
     * @return Room
     */
    RoomDto getByRoomNumber(String roomNumber);

    /**
     * Get all Rooms
     *
     * @return list of Rooms
     */
    List<RoomDto> getAll();

    /**
     * Update Room details
     *
     * @param id      Room id
     * @param roomDto Room details to update
     * @return updated Room
     */
    RoomDto update(Long id, RoomDto roomDto);
}
