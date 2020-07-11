package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.QuestionDto;
import com.ra.course.com.stackoverflow.dto.SessionMemberDto;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBER_ATTR;

public class ViewedQuestionsFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        chain.doFilter(request, response);

        final var httpRequest = (HttpServletRequest) request;
        final var sessionMember = (SessionMemberDto) httpRequest.getSession().getAttribute(MEMBER_ATTR);

        final var question = (QuestionDto) httpRequest.getAttribute("question");

        if (Objects.nonNull(sessionMember) && Objects.nonNull(question)) {
            sessionMember.getViewedQuestions().put(question.getId(), question.getTitle());
        }
    }
}