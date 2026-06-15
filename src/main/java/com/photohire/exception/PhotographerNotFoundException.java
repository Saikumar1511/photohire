package com.photohire.exception;

public class PhotographerNotFoundException
        extends RuntimeException {

    public PhotographerNotFoundException(
            String message) {

        super(message);
    }
}