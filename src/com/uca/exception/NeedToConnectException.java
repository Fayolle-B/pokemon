package com.uca.exception;

public class NeedToConnectException extends FailedLoginException{
    public NeedToConnectException(String message) {
        super(message);
    }
    public  NeedToConnectException(){
        super("You need to be connected to access this page");
    }
}
