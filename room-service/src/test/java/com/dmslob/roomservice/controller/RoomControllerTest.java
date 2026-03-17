package com.dmslob.roomservice.controller;

import com.dmslob.roomservice.model.RoomDto;
import com.dmslob.roomservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @Test
    void should_return_all_rooms_when_room_number_is_null() {
        // Given
        RoomDto dto1 = new RoomDto(1L, "Deluxe Room", "101", "King Bed", "AVAILABLE");
        RoomDto dto2 = new RoomDto(2L, "Standard Room", "102", "Queen Bed", "RESERVED");
        List<RoomDto> expectedDtos = List.of(dto1, dto2);
        when(roomService.getAll()).thenReturn(expectedDtos);
        // When
        List<RoomDto> result = roomController.getByNumberOrAll(null);
        // Then
        assertEquals(expectedDtos, result);
        verify(roomService).getAll();
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void should_return_all_rooms_when_room_number_is_empty() {
        // Given
        RoomDto dto1 = new RoomDto(1L, "Deluxe Room", "101", "King Bed", "AVAILABLE");
        RoomDto dto2 = new RoomDto(2L, "Standard Room", "102", "Queen Bed", "AVAILABLE");
        List<RoomDto> expectedDtos = List.of(dto1, dto2);
        when(roomService.getAll()).thenReturn(expectedDtos);
        // When
        List<RoomDto> result = roomController.getByNumberOrAll("");
        // Then
        assertEquals(expectedDtos, result);
        verify(roomService).getAll();
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void should_return_room_when_room_number_is_provided() {
        // Given
        String roomNumber = "101";
        RoomDto roomDto = new RoomDto(1L, "Deluxe Room", roomNumber, "King Bed", "AVAILABLE");
        List<RoomDto> expectedDtos = List.of(roomDto);
        when(roomService.getByRoomNumber(roomNumber)).thenReturn(roomDto);
        // When
        List<RoomDto> result = roomController.getByNumberOrAll(roomNumber);
        // Then
        assertEquals(expectedDtos, result);
        verify(roomService).getByRoomNumber(roomNumber);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void should_return_room_when_getting_by_id() {
        // Given
        long id = 1L;
        RoomDto roomDto = new RoomDto(id, "Deluxe Room", "101", "King Bed", "AVAILABLE");
        when(roomService.getById(id)).thenReturn(roomDto);
        // When
        RoomDto result = roomController.getById(id);
        // Then
        assertEquals(roomDto, result);
        verify(roomService).getById(id);
        verifyNoMoreInteractions(roomService);
    }

    @Test
    void should_update_room() {
        // Given
        long id = 1L;
        RoomDto inputDto = new RoomDto(id, "Updated Room", "101", "King Bed", "AVAILABLE");
        RoomDto expectedDto = new RoomDto(id, "Updated Room", "101", "King Bed", "AVAILABLE");
        when(roomService.update(id, inputDto)).thenReturn(expectedDto);
        // When
        RoomDto result = roomController.update(id, inputDto);
        // Then
        assertEquals(expectedDto, result);
        verify(roomService).update(id, inputDto);
        verifyNoMoreInteractions(roomService);
    }
}
