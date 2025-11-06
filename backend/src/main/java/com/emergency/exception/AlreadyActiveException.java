package com.emergency.exception;

public class AlreadyActiveException extends RuntimeException {
    public AlreadyActiveException(String message) {
        super(message);
    }
}
