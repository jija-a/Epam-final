package by.alex.testing.service;

import by.alex.testing.service.impl.CourseServiceImpl;
import by.alex.testing.service.impl.CourseUserServiceImpl;
import by.alex.testing.service.impl.TestServiceImpl;
import by.alex.testing.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {
    }

    public CourseService getCourseService() {
        return CourseServiceImpl.getInstance();
    }

    public CourseUserService getCourseUserService() {
        return CourseUserServiceImpl.getInstance();
    }

    public TestService getTestService() {
        return TestServiceImpl.getInstance();
    }

    public UserService getUserService() {
        return UserServiceImpl.getInstance();
    }
}
