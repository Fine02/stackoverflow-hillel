package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.service.storage.QuestionStorageService;
import lombok.AllArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.TAGS_ATTR;

@WebFilter(urlPatterns = "/*")
@AllArgsConstructor
public class TagListFilter implements Filter {

    private final transient QuestionStorageService service;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var session = httpRequest.getSession();

        if(Objects.isNull(session.getAttribute(TAGS_ATTR))){
            session.setAttribute(TAGS_ATTR, service.getAllTagsName());
        }

        chain.doFilter(request, response);
    }
}
