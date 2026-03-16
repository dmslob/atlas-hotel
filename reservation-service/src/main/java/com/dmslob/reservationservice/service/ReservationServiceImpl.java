package com.dmslob.reservationservice.service;

import com.dmslob.reservationservice.model.ReservationDto;
import com.dmslob.reservationservice.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapper guestMapper;

    @Override
    @Transactional(readOnly = true)
    public ReservationDto getById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(reservation -> guestMapper.map(reservation, ReservationDto.class))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getAll() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .map(guest -> guestMapper.map(guest, ReservationDto.class))
                .toList();
    }
}
