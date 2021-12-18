package by.alex.testing.controller;

/**
 * Exception throws when user did not send to server enough parameters
 * for continue command execution.
 */
public class ParametersException extends Exception {

    /**
     * Default constructor.
     */
    public ParametersException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public ParametersException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public ParametersException(final String message,
                               final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause `{@link Throwable} exception cause
     */
    public ParametersException(final Throwable cause) {
        super(cause);
    }
}
