package com.ra.course.com.stackoverflow.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MAIN_URL;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBER_ATTR;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var sessionMember = httpRequest.getSession().getAttribute(MEMBER_ATTR);

        if(Objects.isNull(sessionMember)){
            httpRequest.getRequestDispatcher(MAIN_URL).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

}

