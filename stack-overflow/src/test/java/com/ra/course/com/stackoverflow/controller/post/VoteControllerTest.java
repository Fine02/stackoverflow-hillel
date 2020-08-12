package com.ra.course.com.stackoverflow.controller.post;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.exception.service.CannotVoteOwnPostException;
import com.ra.course.com.stackoverflow.service.vote.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.ra.course.com.stackoverflow.utils.Constants.ID;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService<Answer> answerVoteService;
    @MockBean
    private VoteService<Question> questionVoteService;
    @MockBean
    private VoteService<Comment> commentVoteService;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = getSessionMemberDto();
    }

    @Test
    void whenVoteUpQuestion() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/up/question/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(questionVoteService).upVote(ID, member);
    }
    @Test
    void whenVoteDownQuestion() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/down/question/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(questionVoteService).downVote(ID, member);
    }
    @Test
    void whenVoteUpAnswer() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/up/question/1/answer/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(answerVoteService).upVote(ID, member);
    }
    @Test
    void whenVoteDownAnswer() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/down/question/1/answer/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(answerVoteService).downVote(ID, member);
    }
    @Test
    void whenVoteUpComment() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/up/question/1/comment/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(commentVoteService).upVote(ID, member);
    }
    @Test
    void whenVoteDownComment() throws Exception {
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/down/question/1/comment/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
        verify(commentVoteService).downVote(ID, member);
    }
    @Test
    void whenVoteThrowException() throws Exception {
        //given
        doThrow(new CannotVoteOwnPostException()).when(questionVoteService).upVote(ID, member);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/vote/up/question/1")
        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        flash().attributeExists("text"),
                        redirectedUrl("/view/question/1")
                ));
        verify(questionVoteService).upVote(ID, member);
    }
}
