package com.dmslob.guestservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "GUEST")
public class Guest {
    @Id
    @Column(name = "GUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "REGISTERED_AT")
    private LocalDateTime registeredAt;
}
