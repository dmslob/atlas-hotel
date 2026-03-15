package com.dmslob.roomservice.service;

import com.dmslob.roomservice.entity.Room;
import com.dmslob.roomservice.model.RoomDto;
import com.dmslob.roomservice.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void should_return_room_when_id_exists() {
        // Given
        Long roomId = 1L;
        Room room = new Room();
        room.setId(roomId);
        RoomDto roomDto = new RoomDto(roomId, "Deluxe Room", "101", "King Bed");
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(modelMapper.map(room, RoomDto.class)).thenReturn(roomDto);
        // When
        RoomDto result = roomService.getById(roomId);
        // Then
        assertEquals(roomDto, result);
        verify(roomRepository).findById(roomId);
        verify(modelMapper).map(room, RoomDto.class);
    }

    @Test
    void should_throw_exception_when_id_not_found() {
        // Given
        Long roomId = 1L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());
        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.getById(roomId));
        // Then
        assertEquals("Room is not found", exception.getMessage());
        verify(roomRepository).findById(roomId);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void should_return_room_when_room_number_exists() {
        // Given
        String roomNumber = "101";
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        RoomDto roomDto = new RoomDto(1L, "Deluxe Room", roomNumber, "King Bed");
        when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(Optional.of(room));
        when(modelMapper.map(room, RoomDto.class)).thenReturn(roomDto);
        // When
        RoomDto result = roomService.getByRoomNumber(roomNumber);
        // Then
        assertEquals(roomDto, result);
        verify(roomRepository).findByRoomNumber(roomNumber);
        verify(modelMapper).map(room, RoomDto.class);
    }

    @Test
    void should_throw_exception_when_room_number_not_found() {
        // Given
        String roomNumber = "999";
        when(roomRepository.findByRoomNumber(roomNumber)).thenReturn(Optional.empty());
        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> roomService.getByRoomNumber(roomNumber));
        // Then
        assertEquals("Room is not found", exception.getMessage());
        verify(roomRepository).findByRoomNumber(roomNumber);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void should_return_list_of_room_when_rooms_exist() {
        // Given
        Room room1 = new Room();
        room1.setId(1L);
        Room room2 = new Room();
        room2.setId(2L);
        List<Room> rooms = List.of(room1, room2);
        RoomDto dto1 = new RoomDto(1L, "Deluxe Room", "101", "King Bed");
        RoomDto dto2 = new RoomDto(2L, "Standard Room", "102", "Queen Bed");
        List<RoomDto> expectedDtos = List.of(dto1, dto2);
        when(roomRepository.findAll()).thenReturn(rooms);
        when(modelMapper.map(room1, RoomDto.class)).thenReturn(dto1);
        when(modelMapper.map(room2, RoomDto.class)).thenReturn(dto2);
        // When
        List<RoomDto> result = roomService.getAll();
        // Then
        assertEquals(expectedDtos, result);
        verify(roomRepository).findAll();
        verify(modelMapper).map(room1, RoomDto.class);
        verify(modelMapper).map(room2, RoomDto.class);
    }

    @Test
    void should_return_empty_list_when_no_rooms_exist() {
        // Given
        List<Room> rooms = List.of();
        when(roomRepository.findAll()).thenReturn(rooms);
        // When
        List<RoomDto> result = roomService.getAll();
        // Then
        assertTrue(result.isEmpty());
        verify(roomRepository).findAll();
        verifyNoInteractions(modelMapper);
    }
}
