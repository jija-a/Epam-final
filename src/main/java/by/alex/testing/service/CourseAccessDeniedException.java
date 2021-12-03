package by.alex.testing.service;

public class CourseAccessDeniedException extends Exception {

    public CourseAccessDeniedException() {
    }

    public CourseAccessDeniedException(String message) {
        super(message);
    }

    public CourseAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseAccessDeniedException(Throwable cause) {
        super(cause);
    }
}
