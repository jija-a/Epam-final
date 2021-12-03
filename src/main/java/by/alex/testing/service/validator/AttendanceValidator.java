package by.alex.testing.service.validator;

import by.alex.testing.controller.MessageConstant;
import by.alex.testing.controller.MessageManager;
import by.alex.testing.domain.Attendance;
import by.alex.testing.domain.AttendanceStatus;

import java.util.ArrayList;
import java.util.List;

public class AttendanceValidator extends BaseValidator {

    public static List<String> validate(Attendance attendance) {
        List<String> errors = new ArrayList<>();
        if (attendance.getMark() != null && attendance.getStatus().equals(AttendanceStatus.NOT_PRESENT)) {
            errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.WRONG_PRESENT_MARK));
        } else {
            if (!isPositiveNumber(attendance.getMark())) {
                errors.add(MessageManager.INSTANCE.getMessage(MessageConstant.NAME_ERROR));
            }
        }
        return errors;
    }
}
