package com.ra.course.com.stackoverflow.controller.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;

@WebFilter(urlPatterns = {"/login", "/registration"})
public class AlreadyLoginFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var sessionMember = httpRequest.getSession().getAttribute("account");

        if(Objects.nonNull(sessionMember)){
            httpRequest.getRequestDispatcher("/members/profile").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
