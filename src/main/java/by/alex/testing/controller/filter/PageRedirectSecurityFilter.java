package by.alex.testing.controller.filter;

import by.alex.testing.controller.CommandName;
import by.alex.testing.controller.PageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "jsp-page-security-filter",
        urlPatterns = "/jsp/*")
public class PageRedirectSecurityFilter extends BaseFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(PageRedirectSecurityFilter.class);

    private String homePath;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        super.init(fConfig);
        logger.info("Initializing 'jsp page security filter'");
        homePath = CommandName.TO_HOME_PAGE;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        logger.info("'/jsp/*' is not allowed, redirecting to index");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.sendRedirect(req.getContextPath() + PageConstant.INDEX_PAGE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("'Jsp page security filter' destroyed");
    }
}
