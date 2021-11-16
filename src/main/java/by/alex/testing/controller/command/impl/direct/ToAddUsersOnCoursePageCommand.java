package by.alex.testing.controller.command.impl.direct;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToAddUsersOnCoursePageCommand implements Command {

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        return new ViewResolver(PageConstant.ADD_USERS_ON_COURSE);
    }
}
