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

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static org.mockito.Mockito.*;

public class ModerFilterTest {

    private ModerFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        filter = new ModerFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = mock(FilterChain.class);

        member = getSessionMemberDto();
    }

    @Test
    void whenWithoutSessionAttribute() throws IOException, ServletException {
        //when
        filter.doFilter(request, response, chain);
        //then
        verify(chain, never()).doFilter(request,response);
    }

    @Test
    void whenMemberIsUser() throws IOException, ServletException {
        //given
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, chain);
        //then
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void whenMemberIsModer() throws IOException, ServletException {
        //given
        member.setRole(AccountRole.MODERATOR);
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, chain);
        //when
        verify(chain).doFilter(request, response);
    }
    @Test
    void whenMemberIsAdmin() throws IOException, ServletException {
        //given
        member.setRole(AccountRole.ADMIN);
        request.getSession().setAttribute("account", member);
        //when
        filter.doFilter(request, response, chain);
        //when
        verify(chain).doFilter(request, response);
    }
}
