package by.alex.testing.controller.command.impl.student;

import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.domain.TestResult;
import by.alex.testing.domain.User;
import by.alex.testing.service.ServiceException;
import by.alex.testing.service.ServiceFactory;
import by.alex.testing.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserResultsCommand implements Command {

    private final TestService testService;

    public ShowUserResultsCommand() {
        this.testService = ServiceFactory.getInstance().getTestService();
    }

    @Override
    public ViewResolver execute(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServiceException {

        User user = (User) req.getSession().getAttribute(RequestConstant.USER);
        long userId = user.getId();

        List<TestResult> results = testService.showUserResults(userId);

        req.setAttribute(RequestConstant.RESULTS, results);
        return new ViewResolver(PageConstant.USER_RESULTS);
    }
}
