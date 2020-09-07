package com.ra.course.com.stackoverflow.controller.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class AuthorizationFilterTest {

    private AuthorizationFilter filter;
    private HttpServletRequest  request;
    private HttpServletResponse  response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        filter = new AuthorizationFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void testWithOutSessionAttribute() throws Exception {
        //given
        request.getSession().setAttribute("account", new Object());
        //when
        filter.doFilter(request, response, filterChain);
        //then
        verify(filterChain).doFilter(request, response);
    }
    @Test
    public void testWithSessionAttribute() throws Exception {
        //when
        filter.doFilter(request, response, filterChain);
        //then
        verify(filterChain, never()).doFilter(request,response);
    }
}
