package java.com.uca.exception;

/**
 * Thrown when the user attempts to access a route that is not allowed for him, or a route that is not constructed correctly.
 * (i.e. bad request)
 */
public class IllegalRouteException extends RuntimeException{
    public IllegalRouteException(String message) {
        super(message);
    }
}
