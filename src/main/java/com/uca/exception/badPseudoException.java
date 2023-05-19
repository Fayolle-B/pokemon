package com.uca.exception;


/**
 * Thrown when a user attempts to create a new user with an PSEUDO that already exists in the database.
 */
public class badPseudoException extends RuntimeException{
    public badPseudoException(String message) {
        super(message);
    }
}
