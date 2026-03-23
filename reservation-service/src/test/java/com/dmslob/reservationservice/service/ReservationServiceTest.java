package com.dmslob.reservationservice.service;

import com.dmslob.reservationservice.entity.Reservation;
import com.dmslob.reservationservice.exception.ReservationNotFoundException;
import com.dmslob.reservationservice.model.ReservationDto;
import com.dmslob.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ModelMapper guestMapper;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void should_return_reservation_when_id_exists() {
        // Given
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        ReservationDto reservationDto = new ReservationDto(reservationId, 1L, 1L, LocalDateTime.of(2026, 3, 20, 14, 0), LocalDateTime.of(2026, 3, 25, 11, 0), "CONFIRMED");
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(guestMapper.map(reservation, ReservationDto.class)).thenReturn(reservationDto);
        // When
        ReservationDto result = reservationService.getById(reservationId);
        // Then
        assertEquals(reservationDto, result);
        verify(reservationRepository).findById(reservationId);
        verify(guestMapper).map(reservation, ReservationDto.class);
    }

    @Test
    void should_throw_exception_when_reservation_not_found() {
        // Given
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
        // When & Then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getById(reservationId));
        verify(reservationRepository).findById(reservationId);
        verifyNoInteractions(guestMapper);
    }

    @Test
    void should_return_list_of_reservations() {
        // Given
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        List<Reservation> reservations = List.of(reservation1, reservation2);
        ReservationDto dto1 = new ReservationDto(1L, 1L, 1L, LocalDateTime.of(2026, 3, 20, 14, 0), LocalDateTime.of(2026, 3, 25, 11, 0), "CONFIRMED");
        ReservationDto dto2 = new ReservationDto(2L, 2L, 2L, LocalDateTime.of(2026, 4, 1, 15, 0), LocalDateTime.of(2026, 4, 5, 10, 0), "PENDING");
        List<ReservationDto> expectedDtos = List.of(dto1, dto2);
        when(reservationRepository.findAll()).thenReturn(reservations);
        when(guestMapper.map(reservation1, ReservationDto.class)).thenReturn(dto1);
        when(guestMapper.map(reservation2, ReservationDto.class)).thenReturn(dto2);
        // When
        List<ReservationDto> result = reservationService.getAll();
        // Then
        assertEquals(expectedDtos, result);
        verify(reservationRepository).findAll();
        verify(guestMapper).map(reservation1, ReservationDto.class);
        verify(guestMapper).map(reservation2, ReservationDto.class);
    }

    @Test
    void should_return_empty_list_when_no_reservations() {
        // Given
        List<Reservation> reservations = List.of();
        when(reservationRepository.findAll()).thenReturn(reservations);
        // When
        List<ReservationDto> result = reservationService.getAll();
        // Then
        assertTrue(result.isEmpty());
        verify(reservationRepository).findAll();
        verifyNoInteractions(guestMapper);
    }
}

