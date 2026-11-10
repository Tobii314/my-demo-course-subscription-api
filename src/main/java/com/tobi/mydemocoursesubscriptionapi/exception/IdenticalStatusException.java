package com.tobi.mydemocoursesubscriptionapi.exception;

public class IdenticalStatusException extends RuntimeException {
    public IdenticalStatusException(String message) {
        super(message);
    }
}
