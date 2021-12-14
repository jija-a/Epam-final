package by.alex.testing.dao.pool;

public class InitializingError extends Error{

    public InitializingError() {
    }

    public InitializingError(String message) {
        super(message);
    }

    public InitializingError(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializingError(Throwable cause) {
        super(cause);
    }
}
