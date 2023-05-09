package com.uca.exception;

public class BadEmailException extends RuntimeException{
    public BadEmailException(String message) {
        super(message);
    }
}
