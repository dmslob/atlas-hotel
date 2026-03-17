package com.dmslob.guestservice.exception;

import java.io.Serial;

public class GuestNotFoundException extends  RuntimeException {
    @Serial
    private static final long serialVersionUID = -751184355593199255L;

    public GuestNotFoundException(String message) {
        super(message);
    }
}
