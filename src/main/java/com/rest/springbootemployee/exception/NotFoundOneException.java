package com.rest.springbootemployee.exception;

public class NotFoundOneException extends RuntimeException {
    public NotFoundOneException(String message) {
        super(message + " not found");
    }
}
