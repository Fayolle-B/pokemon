package com.uca.exception;


/**
 * Thrown when a user attempts to access a page that requires him to be connected, but he is not.
 */
public class NeedToConnectException extends FailedLoginException{
    public NeedToConnectException(String message) {
        super(message);
    }
    public  NeedToConnectException(){
        super("You need to be connected to access this page");
    }
}
