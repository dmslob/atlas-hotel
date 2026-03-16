package com.dmslob.roomservice.service;

import com.dmslob.roomservice.model.RoomDto;
import com.dmslob.roomservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto getById(Long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> modelMapper.map(room, RoomDto.class))
                .orElseThrow(() -> new RuntimeException("Room is not found"));
    }

    @Override
    public RoomDto getByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .map(room -> modelMapper.map(room, RoomDto.class))
                .orElseThrow(() -> new RuntimeException("Room is not found"));
    }

    @Override
    public List<RoomDto> getAll() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .map(guest -> modelMapper.map(guest, RoomDto.class))
                .toList();
    }
}
