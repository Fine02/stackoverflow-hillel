package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminFilterTest {

    private AdminFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        filter = new AdminFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = mock(FilterChain.class);

        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("Member name");
            member.setRole(AccountRole.USER);
    }

    @Test
    void whenWithoutSessionAttribute() throws IOException, ServletException {
        //when
        filter.doFilter(request, response, chain);
        //then
        verify(chain, never()).doFilter(request, response);
    }
    @Test
    void whenWithSessionAttributeUser() throws IOException, ServletException {
        //given
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, chain);
        //then
        verify(chain, never()).doFilter(request, response);
    }
    @Test
    void whenWithSessionAttributeAdmin() throws IOException, ServletException {
        //given
        member.setRole(AccountRole.ADMIN);
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, chain);
        //then
        verify(chain).doFilter(request, response);
    }
}
