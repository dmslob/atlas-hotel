package com.dmslob.guestservice.controller;

import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.service.GuestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuestControllerTest {
    private static final String CORRELATION_ID = "correlation-id";

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    @Test
    void should_return_list_of_guests_when_getting_all() {
        // Given
        GuestDto dto1 = new GuestDto(1L, "John", "Doe", "john@example.com", "USA", null, LocalDateTime.now());
        GuestDto dto2 = new GuestDto(2L, "Jane", "Smith", "jane@example.com", "USA", null, LocalDateTime.now());
        List<GuestDto> expectedDtos = List.of(dto1, dto2);
        when(guestService.getAll()).thenReturn(expectedDtos);
        // When
        List<GuestDto> result = guestController.getAll(CORRELATION_ID);
        // Then
        assertEquals(expectedDtos, result);
        verify(guestService).getAll();
    }

    @Test
    void should_return_guest_when_getting_by_id() {
        // Given
        Long guestId = 1L;
        GuestDto guestDto = new GuestDto(guestId, "John", "Doe", "john@example.com", "USA", "123-456-7890", LocalDateTime.now());
        when(guestService.getById(guestId)).thenReturn(guestDto);
        // When
        GuestDto result = guestController.getById(CORRELATION_ID, guestId);
        // Then
        assertEquals(guestDto, result);
        verify(guestService).getById(guestId);
    }

    @Test
    void should_return_optional_guest_when_getting_by_email_and_guest_exists() {
        // Given
        String email = "john@example.com";
        GuestDto expected = new GuestDto(1L, "John", "Doe", email, "USA", "123-456-7890", LocalDateTime.now());
        when(guestService.getByEmail(email)).thenReturn(expected);
        // When
        GuestDto result = guestController.getByEmail(email);
        // Then
        assertEquals(expected, result);
        verify(guestService).getByEmail(email);
    }

    @Test
    void should_create_guest() {
        // Given
        var guestDto = new GuestDto(null, "John", "Doe", "john@example.com", "USA", "123-456-7890", LocalDateTime.now());
        // When
        guestController.create(guestDto);
        // Then
        verify(guestService).create(guestDto);
    }
}
