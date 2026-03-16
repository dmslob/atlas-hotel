package com.dmslob.reservationservice.service;

import com.dmslob.reservationservice.model.ReservationDto;

import java.util.List;

public interface ReservationService {
    /**
     * Get Reservation by id
     *
     * @param reservationId Reservation id
     * @return Reservation
     */
    ReservationDto getById(Long reservationId);

    /**
     * Get all Reservations
     *
     * @return list of Reservations
     */
    List<ReservationDto> getAll();
}
