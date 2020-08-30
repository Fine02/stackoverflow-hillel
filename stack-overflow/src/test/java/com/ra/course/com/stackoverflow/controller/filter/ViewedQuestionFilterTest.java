package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ViewedQuestionFilterTest {

    private ViewedQuestionsFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    
    private QuestionFullDto question;
    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        filter = new ViewedQuestionsFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        
        question = getQuestionFullDto();
        member = getSessionMemberDto();
    }

    @Test
    public void testWithAllAttributes() throws Exception {
        //given
        request.getSession().setAttribute("account", member);
        request.setAttribute("question", question);
        //when
        filter.doFilter(request, response, filterChain);
        //then
        assertTrue(member.getViewedQuestions().containsKey(question.getId()));
    }

    @Test
    public void testWithOnlySessionAttribute() throws Exception {
        //given
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, filterChain);
        //then
        assertTrue(member.getViewedQuestions().isEmpty());
    }

    @Test
    public void testWithoutSessionAttribute() throws Exception {
        //given
        request.setAttribute("question", question);
        //when
        filter.doFilter(request, response, filterChain);
        //then
        verify(filterChain).doFilter(request, response);
    }
}
