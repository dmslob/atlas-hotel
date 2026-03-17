package com.dmslob.reservationservice.service;

import com.dmslob.reservationservice.entity.Reservation;
import com.dmslob.reservationservice.exception.ReservationNotFoundException;
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
                .orElseThrow(() -> new ReservationNotFoundException("Reservation is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDto> getAll() {
        return StreamSupport.stream(reservationRepository.findAll().spliterator(), false)
                .map(guest -> guestMapper.map(guest, ReservationDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void create(ReservationDto reservationDto) {
        var toSave = guestMapper.map(reservationDto, Reservation.class);
        reservationRepository.save(toSave);
    }

    @Override
    @Transactional
    public ReservationDto update(Long reservationId, ReservationDto reservationDto) {
        var existing = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation is not found"));
        existing.setRoomId(reservationDto.getRoomId());
        existing.setGuestId(reservationDto.getGuestId());
        existing.setDateIn(reservationDto.getDateIn());
        existing.setDateOut(reservationDto.getDateOut());
        existing.setStatus(reservationDto.getStatus());
        var updated = reservationRepository.save(existing);
        return guestMapper.map(updated, ReservationDto.class);
    }

    @Override
    @Transactional
    public void deleteById(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
