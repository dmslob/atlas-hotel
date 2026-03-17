package com.dmslob.roomservice.service;

import com.dmslob.roomservice.model.RoomDto;
import com.dmslob.roomservice.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public RoomDto getById(Long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> modelMapper.map(room, RoomDto.class))
                .orElseThrow(() -> new RuntimeException("Room is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDto getByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .map(room -> modelMapper.map(room, RoomDto.class))
                .orElseThrow(() -> new RuntimeException("Room is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> getAll() {
        return StreamSupport.stream(roomRepository.findAll().spliterator(), false)
                .map(guest -> modelMapper.map(guest, RoomDto.class))
                .toList();
    }

    @Override
    @Transactional
    public RoomDto update(Long id, RoomDto roomDto) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room is not found"));
        room.setName(roomDto.getName());
        room.setStatus(roomDto.getStatus());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setBedInfo(roomDto.getBedInfo());
        var updated = roomRepository.save(room);

        return modelMapper.map(updated, RoomDto.class);
    }
}
