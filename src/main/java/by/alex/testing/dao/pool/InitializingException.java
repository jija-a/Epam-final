package by.alex.testing.dao.pool;

public class InitializingException extends Error{

    public InitializingException() {
    }

    public InitializingException(String message) {
        super(message);
    }

    public InitializingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializingException(Throwable cause) {
        super(cause);
    }
}
