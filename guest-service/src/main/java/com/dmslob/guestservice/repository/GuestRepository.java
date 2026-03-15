package com.dmslob.guestservice.repository;

import com.dmslob.guestservice.entity.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends CrudRepository<Guest, Long> {
    Optional<Guest> findByEmail(String emailAddress);
}
