package com.dmslob.guestservice.service;

import com.dmslob.guestservice.model.GuestDto;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    /**
     * Get Guest by id
     * @param guestId Guest id
     * @return GuestDto
     */
    Optional<GuestDto> getById(Long guestId);

    /**
     * Get Guest by email
     * @param emailAddress email address of Guest
     * @return GuestDto
     */
    Optional<GuestDto> getByEmail(String emailAddress);

    /**
     * Get all Guests
     * @return list of GuestDto
     */
    List<GuestDto> getAll();
}
