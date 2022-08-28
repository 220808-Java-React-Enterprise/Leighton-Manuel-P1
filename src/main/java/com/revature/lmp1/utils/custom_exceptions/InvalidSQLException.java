package com.revature.lmp1.utils.custom_exceptions;

public class InvalidSQLException extends RuntimeException {

    public InvalidSQLException() {
    }

    public InvalidSQLException(String message) {
        super(message);
    }

}
