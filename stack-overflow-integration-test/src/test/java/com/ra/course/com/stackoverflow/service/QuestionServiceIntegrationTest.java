//package com.ra.course.com.stackoverflow.service;
//
//import com.ra.course.com.stackoverflow.entity.Answer;
//import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
//import com.ra.course.com.stackoverflow.service.question.QuestionService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class QuestionServiceIntegrationTest {
//
//    @Autowired
//    private QuestionService questionService;
//    private Answer answer;
//
//
//    @Test
//    public void whenAddAnswerToOpenQuestionThenReturnUpdatedQuestion() {
//        Answer.builder().creationDate(LocalDateTime.now()).answerText("Answer for first question").authorId(1L).
//        var resultQuestion = questionService.addAnswerToQuestion(answer);
//
//        assertThat(question.getAnswerList()).contains(answer);
//    }
//}
