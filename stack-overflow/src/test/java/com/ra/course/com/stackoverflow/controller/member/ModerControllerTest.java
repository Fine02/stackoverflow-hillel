package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.member.ModerateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModerController.class)
public class ModerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModerateService service;

    private SessionMemberDto member;
    private QuestionFullDto question;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(ID);
            member.setName("Member name");
            member.setRole(AccountRole.MODERATOR);

        question = new QuestionFullDto();
            question.setId(ID);
            question.setTitle("Title");
            question.setText("Text");
            question.setAuthor(ID);
    }

    @Test
    void whenCloseQuestion() throws Exception {
        //given
        when(service.closeQuestion(ID, member)).thenReturn(question);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/moder/question/1/close")
                    .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/question"),
                        model().attribute("question", question)
                ));
    }

    @Test
    void whenReopenQuestion() throws Exception {
        //given
        when(service.reopenQuestion(ID, member)).thenReturn(question);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/moder/question/1/reopen")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/question"),
                        model().attribute("question", question)
                ));
    }

    @Test
    void whenUndeleteQuestion() throws Exception {
        //given
        when(service.undeleteQuestion(ID, member)).thenReturn(question);
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/moder/question/1/undelete")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/question"),
                        model().attribute("question", question)
                ));
    }
}
