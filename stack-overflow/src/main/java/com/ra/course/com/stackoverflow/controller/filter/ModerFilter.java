package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/moder/*")
public class ModerFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var account = (SessionMemberDto) httpRequest.getSession().getAttribute("account");

        if(Objects.isNull(account) ||
        (!account.getRole().equals(AccountRole.MODERATOR) && !account.getRole().equals(AccountRole.ADMIN))){
            httpRequest.getRequestDispatcher("/login").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
