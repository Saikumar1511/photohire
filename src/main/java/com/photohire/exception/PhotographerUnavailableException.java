package com.photohire.exception;

public class PhotographerUnavailableException
        extends RuntimeException {

    public PhotographerUnavailableException(
            String message) {

        super(message);
    }
}