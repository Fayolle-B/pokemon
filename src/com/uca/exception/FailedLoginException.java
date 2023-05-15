package com.uca.exception;

public class FailedLoginException extends Exception{
    public FailedLoginException() {
        super();
        System.err.println("Unable to connect");

    }

    public FailedLoginException(String message) {
        super(message);
        System.err.println("Unable to connect");

    }
}
