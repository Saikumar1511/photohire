package com.photohire.exception;

public class AvailabilityNotFoundException
        extends RuntimeException {

    public AvailabilityNotFoundException(
            String message) {

        super(message);
    }
}