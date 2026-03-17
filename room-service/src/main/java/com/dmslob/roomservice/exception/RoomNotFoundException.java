package com.dmslob.roomservice.exception;

import java.io.Serial;

public class RoomNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8165061511056204212L;

    public RoomNotFoundException(String message) {
        super(message);
    }
}
