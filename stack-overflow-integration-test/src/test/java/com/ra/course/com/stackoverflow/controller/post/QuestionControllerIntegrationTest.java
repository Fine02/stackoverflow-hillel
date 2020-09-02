package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.CreateQuestionDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.dto.post.UpdateQuestionDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionMemberDto member;
    private QuestionFullDto question;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);

        question = new QuestionFullDto();
            question.setId(1L);
            question.setTitle("First question title");
            question.setText("First question description");
            question.setAuthor(1L);
            question.setStatus(QuestionStatus.OPEN);
    }

    @Test
    @Rollback
    @Transactional
    void whenCreateQuestion() throws Exception {

        question.setId(4L);
        question.setTitle("New question title");
        question.setText("New question description");

        mockMvc.perform(post("/questions/create")
            .sessionAttr("account", member)
            .param("text", "New question description")
            .param("title", "New question title"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attribute("question", question),
                        view().name("view/question")
                ));
    }

    @Test
    @Rollback
    @Transactional
    void whenDeleteQuestion() throws Exception {

        question.setStatus(QuestionStatus.DELETED);

        mockMvc.perform(post("/questions/1/delete")
            .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attribute("question", question),
                        view().name("view/question")
                ));
    }

    @Test
    @Rollback
    @Transactional
    void whenPostUpdateQuestionThenReturnModelWithDto() throws Exception {

        final var dto = new UpdateQuestionDto();
            dto.setText("New text");
            dto.setTitle("New title");

        question.setText("New text");
        question.setTitle("New title");

        mockMvc.perform(post("/questions/1/update")
                .sessionAttr("account", member)
                .param("text", "New text")
                .param("title", "New title"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attribute("question", question),
                        view().name("view/question")
                ));
    }


    @Test
    @Rollback
    @Transactional
    void whenCloseQuestion() throws Exception {

        question.setStatus(QuestionStatus.CLOSE);

        mockMvc.perform(post("/questions/1/close")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        model().attribute("question", question),
                        view().name("view/question")
                ));
    }

}
