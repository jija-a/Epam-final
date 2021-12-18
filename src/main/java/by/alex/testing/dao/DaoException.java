package by.alex.testing.dao;

public class DaoException extends Exception {

    /**
     * Default constructor.
     */
    public DaoException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public DaoException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public DaoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause `{@link Throwable} exception cause
     */
    public DaoException(final Throwable cause) {
        super(cause);
    }
}
