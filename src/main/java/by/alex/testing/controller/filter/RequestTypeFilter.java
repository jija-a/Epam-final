package by.alex.testing.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestTypeFilter extends BaseFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        boolean isPost = "POST".equals(request.getMethod());
        if (isPost) {

        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
