package com.dmslob.reservationservice.controller;

import com.dmslob.reservationservice.exception.ReservationNotFoundException;
import com.dmslob.reservationservice.model.ReservationDto;
import com.dmslob.reservationservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Test
    void should_return_all_reservations_when_getting_all() {
        // Given
        ReservationDto dto1 = new ReservationDto(1L, 1L, 1L, LocalDateTime.of(2026, 3, 20, 14, 0), LocalDateTime.of(2026, 3, 25, 11, 0), "CONFIRMED");
        ReservationDto dto2 = new ReservationDto(2L, 2L, 2L, LocalDateTime.of(2026, 4, 1, 15, 0), LocalDateTime.of(2026, 4, 5, 10, 0), "PENDING");
        List<ReservationDto> expectedDtos = List.of(dto1, dto2);
        when(reservationService.getAll()).thenReturn(expectedDtos);
        // When
        List<ReservationDto> result = reservationController.getAll();
        // Then
        assertEquals(expectedDtos, result);
        verify(reservationService).getAll();
    }

    @Test
    void should_return_reservation_when_getting_by_id() {
        // Given
        Long reservationId = 1L;
        ReservationDto reservationDto = new ReservationDto(reservationId, 1L, 1L, LocalDateTime.of(2026, 3, 20, 14, 0), LocalDateTime.of(2026, 3, 25, 11, 0), "CONFIRMED");
        when(reservationService.getById(reservationId)).thenReturn(reservationDto);
        // When
        ReservationDto result = reservationController.getById(reservationId);
        // Then
        assertEquals(reservationDto, result);
        verify(reservationService).getById(reservationId);
    }

    @Test
    void should_throw_exception_when_getting_by_id_and_reservation_not_found() {
        // Given
        Long reservationId = 999L;
        when(reservationService.getById(reservationId))
                .thenThrow(new ReservationNotFoundException("Reservation not found"));
        // When & Then
        assertThrows(ReservationNotFoundException.class, () -> reservationController.getById(reservationId));
        verify(reservationService).getById(reservationId);
    }

    @Test
    void should_return_empty_list_when_no_reservations_exist() {
        // Given
        when(reservationService.getAll()).thenReturn(List.of());
        // When
        List<ReservationDto> result = reservationController.getAll();
        // Then
        assertEquals(List.of(), result);
        verify(reservationService).getAll();
    }
}

