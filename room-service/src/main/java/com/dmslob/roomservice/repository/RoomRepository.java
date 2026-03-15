package com.dmslob.roomservice.repository;

import com.dmslob.roomservice.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
}
