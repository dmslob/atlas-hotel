package com.dmslob.guestservice.service;

import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.repository.GuestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;
    private final ModelMapper guestMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<GuestDto> getById(Long guestId) {
        return Optional.of(guestRepository.findById(guestId)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .orElseThrow(() -> new RuntimeException("Guest is not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GuestDto> getByEmail(String emailAddress) {
        return Optional.of(guestRepository.findByEmailAddress(emailAddress)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .orElseThrow(() -> new RuntimeException("Guest is not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> getAll() {
        return StreamSupport.stream(guestRepository.findAll().spliterator(), false)
                .map(guest -> guestMapper.map(guest, GuestDto.class))
                .toList();
    }
}
