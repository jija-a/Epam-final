package by.alex.testing;

import by.alex.testing.domain.Test;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.TestService;
import by.alex.testing.service.impl.TestServiceImpl;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        long courseId = 1;
        TestService testService = new TestServiceImpl();
        try {
            testService.removeTestFromCourse(5);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
