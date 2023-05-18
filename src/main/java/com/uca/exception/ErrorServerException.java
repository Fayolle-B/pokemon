package java.com.uca.exception;

public class ErrorServerException extends Exception {
    public ErrorServerException() {
        super();
        System.err.println("Internal Server Error");

    }

    public ErrorServerException(String message) {
        super(message);
        System.err.println("Internal Server Error");

    }
}
