package by.alex.testing.domain;

/**
 * Exception throws when {@link Entity} can't be resolved by id.
 *
 * @see AttendanceStatus
 * @see UserCourseStatus
 * @see UserRole
 */
public class UnknownEntityException extends RuntimeException {

    /**
     * Default constructor.
     */
    public UnknownEntityException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     */
    public UnknownEntityException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message {@link String} exception message
     * @param cause   {@link Throwable} exception cause
     */
    public UnknownEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param cause `{@link Throwable} exception cause
     */
    public UnknownEntityException(final Throwable cause) {
        super(cause);
    }
}
