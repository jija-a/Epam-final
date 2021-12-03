package by.alex.testing.domain;

public class UnknownEntityException extends RuntimeException {

    public UnknownEntityException() {
    }

    public UnknownEntityException(String message) {
        super(message);
    }

    public UnknownEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEntityException(Throwable cause) {
        super(cause);
    }
}
