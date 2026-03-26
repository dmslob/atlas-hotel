package com.dmslob.reservationservice.service;

import com.dmslob.reservationservice.entity.Reservation;
import com.dmslob.reservationservice.exception.ReservationNotFoundException;
import com.dmslob.reservationservice.model.ReservationDto;
import com.dmslob.reservationservice.repository.ReservationRepository;
import com.dmslob.reservationservice.service.client.GuestFeignClient;
import com.dmslob.reservationservice.service.client.RoomFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapper guestMapper;
    private final GuestFeignClient guestFeignClient;
    private final RoomFeignClient roomFeignClient;

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
    public void create(String correlationId, ReservationDto reservationDto) {
        log.info("Creating new reservation for guest id {} and room id {}",
                reservationDto.getGuestId(), reservationDto.getRoomId());
        var guest = guestFeignClient.findById(correlationId,
                reservationDto.getGuestId());
        // todo: handle the case when guest is not found
        if (Objects.nonNull(guest)) {
            log.info("Guest {} with id {} found", guest.getFirstName(), guest.getId());
        }
        // todo: handle the case when room is not found
        var room = roomFeignClient.findById(reservationDto.getRoomId());
        if (Objects.nonNull(room)) {
            log.info("Room {} with id {} found", room.getRoomNumber(), room.getId());
        }
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
