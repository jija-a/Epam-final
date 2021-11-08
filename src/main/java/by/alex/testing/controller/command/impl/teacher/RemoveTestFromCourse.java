package by.alex.testing.controller.command.impl.teacher;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.command.Command;
import by.alex.testing.controller.resolver.ViewResolver;
import by.alex.testing.service.CourseService;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.impl.CourseServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveTestFromCourse implements Command {

    private final CourseService service;

    public RemoveTestFromCourse() {
        this.service = new CourseServiceImpl();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        long testId = Long.parseLong(req.getParameter(RequestConstant.TEST_ID));

        service.removeTestFromCourse(testId);
        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}