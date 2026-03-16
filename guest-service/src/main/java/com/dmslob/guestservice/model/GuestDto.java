package com.dmslob.guestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GuestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String phoneNumber;
    private LocalDateTime registeredAt;
}
