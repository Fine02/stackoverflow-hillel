package com.ra.course.com.stackoverflow.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MAIN_URL;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBER_ATTR;

public class MemberFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var memberDto = httpRequest.getSession().getAttribute(MEMBER_ATTR);

        if(Objects.nonNull(memberDto)){
            httpRequest.getRequestDispatcher(MAIN_URL).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
