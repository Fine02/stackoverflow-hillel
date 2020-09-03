package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.AnswerDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AnswerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionMemberDto member;
    private AnswerDto answer;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);

        answer = new AnswerDto();
            answer.setQuestion(1L);
            answer.setAuthor(1L);
            answer.setId(1L);
            answer.setText("Some answer text");
    }

    @Test
    @Rollback
    @Transactional
    void whenAddAnswer() throws Exception {

        //create answer
        mockMvc.perform(post("/questions/1/answers/create")
                .sessionAttr("account", member)
                .param("text", "New answer"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check adding
        answer.setText("New answer");
        answer.setId(4L);

        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");

        assertTrue(question.getAnswers().contains(answer));
    }

    @Test
    @Rollback
    @Transactional
    void whenDeleteAnswer() throws Exception {

        //delete answer
        mockMvc.perform(post("/questions/1/answers/1/delete")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check deleting
        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");

        assertFalse(question.getAnswers().contains(answer));
    }

    @Test
    @Rollback
    @Transactional
    void whenAcceptAnswer() throws Exception {

        //accept answer
        mockMvc.perform(post("/questions/1/answers/1/accept")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check accepting
        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");

        answer.setAccepted(true);

        assertTrue(question.getAnswers().contains(answer));
        assertNull(question.getBounty());
    }
}
