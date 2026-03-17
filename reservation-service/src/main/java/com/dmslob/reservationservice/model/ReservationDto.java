package com.dmslob.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDto {
    private Long id;
    private long roomId;
    private long guestId;
    private LocalDateTime dateIn;
    private LocalDateTime dateOut;
    private String status;
}
