package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.service.post.TagService;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/*")
@AllArgsConstructor
public class TagListFilter implements Filter {

    private final TagService service;

    private final static String TAGS = "tags";

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var session = httpRequest.getSession();

        if(Objects.isNull(session.getAttribute(TAGS))){
            session.setAttribute(TAGS, service.getAllTags());
        }

        chain.doFilter(request, response);
    }
}
