package by.alex.testing.controller;

public class NotEnoughParametersException extends Exception {

    public NotEnoughParametersException() {
    }

    public NotEnoughParametersException(String message) {
        super(message);
    }

    public NotEnoughParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughParametersException(Throwable cause) {
        super(cause);
    }
}
