package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;

import java.util.ArrayList;
import java.util.List;

public class AttendanceValidator extends BaseValidator {

    /**
     * Method to validate {@link Attendance} fields.
     *
     * @param att {@link Attendance} to validate
     * @return {@link List} of errors if not successfully validated,
     * empty if validated successful
     */
    public static List<String> validate(final Attendance att) {
        String error =
                MessageManager.INSTANCE.getMessage(MessageConstant.MARK_ERROR);
        AttendanceStatus status = att.getStatus();
        Integer mark = att.getMark();

        List<String> errors = new ArrayList<>();
        if (mark != null && status.equals(AttendanceStatus.NOT_PRESENT)) {
            errors.add(error);
        } else {
            if (!isPositiveNumber(mark)) {
                errors.add(error);
            }
        }
        return errors;
    }
}
