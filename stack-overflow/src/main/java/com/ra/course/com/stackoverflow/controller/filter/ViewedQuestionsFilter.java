package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/view/question/*")
public class ViewedQuestionsFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {

        final var httpRequest = (HttpServletRequest) request;
        final var sessionMember = (SessionMemberDto) httpRequest.getSession().getAttribute("account");

        chain.doFilter(request, response);

        final var question = (QuestionFullDto) httpRequest.getAttribute("question");

        if (Objects.nonNull(sessionMember) && Objects.nonNull(question)) {
            sessionMember.getViewedQuestions().put(question.getId(), question.getTitle());
        }
    }
}