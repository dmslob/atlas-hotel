package com.dmslob.guestservice.service;

import com.dmslob.guestservice.model.GuestDto;

import java.util.List;

public interface GuestService {
    /**
     * Get Guest by id
     *
     * @param guestId Guest id
     * @return GuestDto
     */
    GuestDto getById(Long guestId);

    /**
     * Get Guest by email
     *
     * @param emailAddress email address of Guest
     * @return GuestDto
     */
    GuestDto getByEmail(String emailAddress);

    /**
     * Get all Guests
     *
     * @return list of GuestDto
     */
    List<GuestDto> getAll();

    /**
     * Create a new Guest
     *
     * @param guestDto GuestDto to create
     */
    void create(GuestDto guestDto);
}
