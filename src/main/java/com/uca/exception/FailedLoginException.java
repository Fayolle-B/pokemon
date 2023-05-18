package java.com.uca.exception;


/**
 * Thrown when a user attempts to connect with an invalid login or passwor, or when the database is unreachable, or any other error occurs while attempting to connect.
 */
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
