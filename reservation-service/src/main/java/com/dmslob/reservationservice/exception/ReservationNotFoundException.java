package com.dmslob.reservationservice.exception;

import java.io.Serial;

public class ReservationNotFoundException extends  RuntimeException {
    @Serial
    private static final long serialVersionUID = 8464849555982383539L;

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
