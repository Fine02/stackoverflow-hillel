package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBER_ATTR;

@WebFilter(urlPatterns = "/search/questions")
public class ViewedQuestionsFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var sessionMember = (SessionMemberDto) httpRequest.getSession().getAttribute(MEMBER_ATTR);

        chain.doFilter(request, response);

        final var question = (QuestionDto) httpRequest.getAttribute("question");

        if (Objects.nonNull(sessionMember) && Objects.nonNull(question)) {
            sessionMember.getViewedQuestions().put(question.getId(), question.getTitle());
        }
    }
}