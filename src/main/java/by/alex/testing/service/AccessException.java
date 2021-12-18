package by.alex.testing.service;

/**
 * Exception throws if {@link by.alex.testing.domain.User}
 * does not have enough rights to {@link by.alex.testing.domain.Course}
 * manipulation.
 */
public class AccessException extends Exception {

    /**
     * Default constructor.
     */
    public AccessException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public AccessException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public AccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause {@link Throwable} exception cause
     */
    public AccessException(final Throwable cause) {
        super(cause);
    }
}
