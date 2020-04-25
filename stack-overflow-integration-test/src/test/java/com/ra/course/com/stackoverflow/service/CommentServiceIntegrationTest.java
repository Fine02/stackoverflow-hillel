package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.repository.AnswerRepository;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.comment.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CommentServiceIntegrationTest {

    @Autowired
    private CommentService commentService;
    private Comment comment;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void whenAddCommentToOpenQuestionThenReturnUpdatedQuestion() {
        var question = questionRepository.findById(1L).get();
        comment = createNewComment();

        var resultQuestion = commentService.addCommentToQuestion(comment, question);
        assertThat(resultQuestion.getCommentList()).contains(comment);
    }

    @Test
    public void whenAddCommentToAnswerThenReturnUpdatedAnswer() {
        var answer = answerRepository.findById(1L).get();
        comment = createNewComment();

        var resultAnswer = commentService.addCommentToAnswer(comment, answer);
        assertThat(resultAnswer.getComments()).contains(comment);
    }

    private Comment createNewComment() {
        return Comment.builder()
                .id(null)
                .text("Some new comment for anything")
                .creationDate(LocalDateTime.now())
                .authorId(1L)
                .questionId(1L)
                .build();
    }
}
