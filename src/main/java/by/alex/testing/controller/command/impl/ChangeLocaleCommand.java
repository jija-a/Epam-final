package by.alex.testing.controller.command.impl;

import by.alex.testing.controller.MessageManager;
import by.alex.testing.controller.PageConstant;
import by.alex.testing.controller.RequestConstant;
import by.alex.testing.controller.ViewResolver;
import by.alex.testing.controller.command.Command;
import by.alex.testing.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class ChangeLocaleCommand implements Command {

    @Override
    public ViewResolver execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {

        HttpSession session = req.getSession();

        Locale locale = (Locale) session.getAttribute(RequestConstant.LOCALE);
        String language = locale == null ? "en" : locale.getLanguage();

        if (language.equals("en")) {
            session.setAttribute(RequestConstant.LOCALE, new Locale("ru", "RU"));
            MessageManager.INSTANCE.changeLocale(new Locale("ru", "RU"));
        } else if (language.equals("ru")) {
            MessageManager.INSTANCE.changeLocale(new Locale("en", "US"));
            session.setAttribute(RequestConstant.LOCALE, new Locale("en", "US"));
        }

        return new ViewResolver(PageConstant.HOME_PAGE);
    }
}
