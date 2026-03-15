package com.dmslob.guestservice.service;

import com.dmslob.guestservice.entity.Guest;
import com.dmslob.guestservice.model.GuestDto;
import com.dmslob.guestservice.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ModelMapper guestMapper;

    @InjectMocks
    private GuestServiceImpl guestService;

    @Test
    void should_return_guest_when_id_exists() {
        // Given
        Long guestId = 1L;
        Guest guest = new Guest();
        guest.setId(guestId);
        var guestDto = new GuestDto(
                guestId,
                "John",
                "Doe",
                "john.doe@example.com",
                "123 Main St",
                "USA",
                "CA",
                "123-456-7890");
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(guestMapper.map(guest, GuestDto.class)).thenReturn(guestDto);
        // When
        GuestDto result = guestService.getById(guestId);
        // Then
        assertNotNull(result);
        assertEquals(guestDto, result);
        verify(guestRepository).findById(guestId);
        verify(guestMapper).map(guest, GuestDto.class);
    }

    @Test
    void should_throw_exception_when_id_not_found() {
        // Given
        Long guestId = 1L;
        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());
        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> guestService.getById(guestId));
        // Then
        assertEquals("Guest is not found", exception.getMessage());
        verify(guestRepository).findById(guestId);
        verifyNoInteractions(guestMapper);
    }

    @Test
    void should_return_guest_when_email_exists() {
        // Given
        String email = "john.doe@example.com";
        Guest guest = new Guest();
        guest.setEmail(email);
        GuestDto guestDto = new GuestDto(1L, "John", "Doe", email, "123 Main St", "USA", "CA", "123-456-7890");
        when(guestRepository.findByEmail(email)).thenReturn(Optional.of(guest));
        when(guestMapper.map(guest, GuestDto.class)).thenReturn(guestDto);
        // When
        GuestDto result = guestService.getByEmail(email);
        // Then
        assertNotNull(result);
        assertEquals(guestDto, result);
        verify(guestRepository).findByEmail(email);
        verify(guestMapper).map(guest, GuestDto.class);
    }

    @Test
    void should_throw_exception_when_email_not_found() {
        // Given
        String email = "nonexistent@example.com";
        when(guestRepository.findByEmail(email)).thenReturn(Optional.empty());
        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> guestService.getByEmail(email));
        // Then
        assertEquals("Guest is not found", exception.getMessage());
        verify(guestRepository).findByEmail(email);
        verifyNoInteractions(guestMapper);
    }

    @Test
    void should_return_list_of_guests_when_exist() {
        // Given
        Guest guest1 = new Guest();
        guest1.setId(1L);
        Guest guest2 = new Guest();
        guest2.setId(2L);
        List<Guest> guests = List.of(guest1, guest2);
        GuestDto dto1 = new GuestDto(1L, "John", "Doe", "john@example.com", null, null, null, null);
        GuestDto dto2 = new GuestDto(2L, "Jane", "Smith", "jane@example.com", null, null, null, null);
        List<GuestDto> expectedDtos = List.of(dto1, dto2);
        when(guestRepository.findAll()).thenReturn(guests);
        when(guestMapper.map(guest1, GuestDto.class)).thenReturn(dto1);
        when(guestMapper.map(guest2, GuestDto.class)).thenReturn(dto2);
        // When
        List<GuestDto> result = guestService.getAll();
        // Then
        assertEquals(expectedDtos, result);
        verify(guestRepository).findAll();
        verify(guestMapper).map(guest1, GuestDto.class);
        verify(guestMapper).map(guest2, GuestDto.class);
    }

    @Test
    void should_return_empty_list_when_no_guests_exist() {
        // Given
        List<Guest> guests = List.of();
        when(guestRepository.findAll()).thenReturn(guests);
        // When
        List<GuestDto> result = guestService.getAll();
        // Then
        assertTrue(result.isEmpty());
        verify(guestRepository).findAll();
        verifyNoInteractions(guestMapper);
    }
}
