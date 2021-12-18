package by.alex.testing.controller.filter;

import by.alex.testing.controller.PageConstant;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter redirects {@link by.alex.testing.domain.User} to
 * index page if they input '/jsp' path.
 */
public final class PageRedirectSecurityFilter extends BaseFilter {

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String page = req.getContextPath() + PageConstant.INDEX_PAGE;
        httpResponse.sendRedirect(page);
    }
}
