package com.ra.course.com.stackoverflow.controller.filter;

import com.ra.course.com.stackoverflow.service.post.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class TagListFilterTest {


    private TagListFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    private final TagService tagService = mock(TagService.class);

    @BeforeEach
    void setUp() {
        filter = new TagListFilter(tagService);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void testWithOutSessionAttribute() throws Exception {
        //when
        filter.doFilter(request, response, filterChain);
        //then
        verify(tagService).getAllTags();
    }
    @Test
    public void testWithSessionAttribute() throws Exception {
        //given
        request.getSession().setAttribute("tags", new Object());
        //when
        filter.doFilter(request, response, filterChain);
        //then
        verify(tagService, never()).getAllTags();
    }

}
