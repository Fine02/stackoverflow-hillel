package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.post.AnswerDto;
import com.ra.course.com.stackoverflow.dto.post.CommentDto;
import com.ra.course.com.stackoverflow.dto.post.CreateDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.post.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionMemberDto member;
    private CommentDto comment;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);

        comment = new CommentDto();
            comment.setId(4L);
            comment.setAuthor(1L);
            comment.setText("New comment");
    }

    @Test
    @Rollback
    @Transactional
    void whenCreateCommentToQuestion() throws Exception {

        //create comment
        mockMvc.perform(post("/questions/1/comments/create")
                .sessionAttr("account", member)
                .param("text", "New comment"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check created comment
        comment.setQuestionId(1L);

        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");

        assertTrue(question.getComments().contains(comment));
    }

    @Test
    @Rollback
    @Transactional
    void whenCreateCommentToAnswer() throws Exception {

        //create comment
        mockMvc.perform(post("/questions/1/comments/answers/1/create")
                .sessionAttr("account", member)
                .param("text", "New comment"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check created comment
        comment.setAnswerId(1L);

        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");
        var answer = question.getAnswers().get(0);

        assertTrue(answer.getComments().contains(comment));
    }

    @Test
    @Rollback
    @Transactional
    void whenDeleteComment() throws Exception {

        //delete comment
        mockMvc.perform(MockMvcRequestBuilders.post("/questions/1/comments/1/delete")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));

        //check deleting
        comment.setId(1L);
        comment.setText("Some comment text");
        comment.setQuestionId(1L);

        var question = (QuestionFullDto) mockMvc.perform(get("/view/question/1"))
                .andReturn().getRequest().getAttribute("question");

        assertFalse(question.getComments().contains(comment));
    }

}

