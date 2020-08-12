package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.service.post.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService service;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = getSessionMemberDto();
    }

    @Test
    void whenCreateCommentToQuestion() throws Exception {
        //given
        var createDto = new CreateDto();
        createDto.setText("Text");
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/create")
                .sessionAttr("account", member)
                .param("text", "Text"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).addCommentToQuestion(createDto, 1L, member);
    }
    @Test
    void whenCreateCommentToAnswer() throws Exception {
        //given
        var createDto = new CreateDto();
        createDto.setText("Text");
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/answers/1/create")
                .sessionAttr("account", member)
                .param("text", "Text"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).addCommentToAnswer(createDto, 1L, member);
    }

    @Test
    void whenDeleteComment() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/1/delete")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).deleteComment( 1L, member);
    }
}

