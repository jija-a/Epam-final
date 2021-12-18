package by.alex.testing.service;

/**
 * Marks exceptions that was thrown in DAO layer.
 * Allows seeing stacktrace.
 */
public class ServiceException extends Exception {

    /**
     * Default constructor.
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause `{@link Throwable} exception cause
     */
    public ServiceException(final Throwable cause) {
        super(cause);
    }
}
