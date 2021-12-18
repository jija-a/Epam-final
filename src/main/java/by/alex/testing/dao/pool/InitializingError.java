package by.alex.testing.dao.pool;

public class InitializingError extends Error {

    /**
     * Default constructor.
     */
    public InitializingError() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public InitializingError(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public InitializingError(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause `{@link Throwable} exception cause
     */
    public InitializingError(final Throwable cause) {
        super(cause);
    }
}
