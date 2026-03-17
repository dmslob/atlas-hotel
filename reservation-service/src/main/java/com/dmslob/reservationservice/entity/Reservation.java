package com.dmslob.reservationservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @Column(name = "RESERVATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GUEST_ID")
    private long guestId;

    @Column(name = "ROOM_ID")
    private long roomId;

    @Column(name = "DATE_IN")
    private LocalDateTime dateIn;

    @Column(name = "DATE_OUT")
    private LocalDateTime dateOut;

    @Column(name = "STATUS")
    private String status;
}
