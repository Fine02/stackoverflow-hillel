package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.post.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    private CreateDto dto;

    @BeforeEach
    void setUp() {
        member = createMember();
        dto = new CreateDto();
            dto.setText("Text");
    }

    @Test
    void whenCreateCommentToQuestion() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/create")
                .sessionAttr("account", member)
                .param("text", "Text"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).addCommentToQuestion(dto, 1L, member);
    }
    @Test
    void whenCreateCommentToAnswer() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/answers/1/create")
                .sessionAttr("account", member)
                .param("text", "Text"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).addCommentToAnswer(dto, 1L, member);
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

    private SessionMemberDto createMember(){
        var member = new SessionMemberDto();
        member.setId(1L);
        member.setName("Member name");
        member.setRole(AccountRole.USER);
        return member;
    }
}

