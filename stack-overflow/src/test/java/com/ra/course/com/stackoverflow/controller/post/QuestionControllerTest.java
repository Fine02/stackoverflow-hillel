package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService service;

    private SessionMemberDto member;
    private QuestionFullDto question;
    
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(ID);
            member.setName("Member name");
            member.setRole(AccountRole.USER);

        question = new QuestionFullDto();
            question.setId(ID);
            question.setTitle("Title");
            question.setText("Text");
            question.setAuthor(ID);
    }

    @Test
    void whenGetCreateQuestionThenReturnModelWithDto() throws Exception {
        mockMvc.perform(get("/questions/create")
            .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("dto"),
                        view().name("question/create")
                ));
    }

    @Test
    void whenPostCreateQuestionThenReturnModelWithDto() throws Exception {
        //given
        final var dto = new CreateQuestionDto();
            dto.setText("Some text");
            dto.setTitle("Some title");
        when(service.createQuestion(dto, member)).thenReturn(question);
        //then
        mockMvc.perform(post("/questions/create")
            .sessionAttr("account", member)
            .param("text", "Some text")
            .param("title", "Some title"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("question", "dto"),
                        view().name("view/question")
                ));
    }

    @Test
    void whenDeleteQuestion() throws Exception {
        //given
        when(service.deleteQuestion(ID, member)).thenReturn(question);
        //then
        mockMvc.perform(post("/questions/1/delete")
            .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("question", "dto"),
                        view().name("view/question")
                ));
    }

    @Test
    void whenGetUpdateQuestionThenReturnModelWithDto() throws Exception {
        mockMvc.perform(get("/questions/update")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("dto"),
                        view().name("question/update")
                ));
    }

    @Test
    void whenPostUpdateQuestionThenReturnModelWithDto() throws Exception {
        //given
        final var dto = new UpdateQuestionDto();
            dto.setText("New text");
            dto.setTitle("New title");
        when(service.updateQuestion(dto,ID, member)).thenReturn(question);
        //then
        mockMvc.perform(post("/questions/1/update")
                .sessionAttr("account", member)
                .param("text", "New text")
                .param("title", "New title"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("question", "dto"),
                        view().name("view/question")
                ));
    }


    @Test
    void whenCloseQuestion() throws Exception {
        //given
        when(service.closeQuestion(ID, member)).thenReturn(question);
        //then
        mockMvc.perform(post("/questions/1/close")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attributeExists("question", "dto"),
                        view().name("view/question")
                ));
    }

}
