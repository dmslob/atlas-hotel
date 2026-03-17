package com.dmslob.guestservice.service;

import com.dmslob.guestservice.entity.Guest;
import com.dmslob.guestservice.exception.GuestNotFoundException;
import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.repository.GuestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;
    private final ModelMapper guestMapper;

    @Override
    @Transactional(readOnly = true)
    public GuestDto getById(Long guestId) {
        return guestRepository.findById(guestId)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .orElseThrow(() -> new GuestNotFoundException("Guest is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public GuestDto getByEmail(String email) {
        return guestRepository.findByEmail(email)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .orElseThrow(() -> new GuestNotFoundException("Guest is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> getAll() {
        return StreamSupport.stream(guestRepository.findAll().spliterator(), false)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void create(GuestDto guestDto) {
        var guest = guestMapper.map(guestDto, Guest.class);
        guestRepository.save(guest);
        log.info("Guest is created");
    }
}
