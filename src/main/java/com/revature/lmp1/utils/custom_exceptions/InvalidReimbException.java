package com.revature.lmp1.utils.custom_exceptions;

public class InvalidReimbException extends RuntimeException {
    public InvalidReimbException() {
    }

    public InvalidReimbException(String message) {
        super(message);
    }
}
