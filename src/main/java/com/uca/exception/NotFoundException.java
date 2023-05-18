package java.com.uca.exception;

public class NotFoundException extends  Exception{
    public NotFoundException() {
        super();
        System.err.println("Page not found");

    }

    public NotFoundException(String message) {
        super(message);
        System.err.println("Page not found");

    }
}
