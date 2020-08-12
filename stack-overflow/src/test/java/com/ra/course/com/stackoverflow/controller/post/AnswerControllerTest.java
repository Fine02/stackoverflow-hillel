package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.service.post.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerController.class)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService service;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = getSessionMemberDto();
    }

    @Test
    void whenAddAnswer() throws Exception {
        //given
        var dto = new CreateDto();
            dto.setText("Text");
        //then
        mockMvc.perform(post("/questions/1/answers/create")
                .sessionAttr("account", member)
                .param("text", "Text"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).addAnswer(dto, 1L, member);
    }

    @Test
    void whenDeleteAnswer() throws Exception {
        //then
        mockMvc.perform(post("/questions/1/answers/1/delete")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).deleteAnswer(1L, member);
    }

    @Test
    void whenAcceptAnswer() throws Exception {
        //then
        mockMvc.perform(post("/questions/1/answers/1/accept")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(service).acceptAnswer(1L, member);
    }
}
