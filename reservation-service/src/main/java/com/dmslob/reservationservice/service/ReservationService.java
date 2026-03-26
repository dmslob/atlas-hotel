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

    /**
     * Create a new Reservation
     *
     * @param correlationId correlation id for tracing
     * @param reservationDto Reservation details
     */
    void create(String correlationId, ReservationDto reservationDto);

    /**
     * Update an existing Reservation
     *
     * @param reservationId  Reservation id
     * @param reservationDto updated Reservation details
     * @return updated Reservation
     */
    ReservationDto update(Long reservationId, ReservationDto reservationDto);

    /**
     * Delete a Reservation by id
     *
     * @param reservationId Reservation id
     */
    void deleteById(Long reservationId);
}
