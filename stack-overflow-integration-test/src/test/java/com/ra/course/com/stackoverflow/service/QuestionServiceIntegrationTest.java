package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.TagAlreadyAddedException;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.TagRepository;
import com.ra.course.com.stackoverflow.service.question.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class QuestionServiceIntegrationTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private Question question;
    private Tag tag;

    @Test
    @DisplayName("Integration test for QuestionService to add tag to question then OK")
    public void whenAddTagToQuestionThenReturnTrue() {
        injectQuestionAndTagPair(1L, "SQL");

        assertTrue(questionService.addTagToQuestion(tag, question));
    }

    @Test
    @DisplayName("Integration test for QuestionService to add tag to question then OK")
    public void ifTagNotFoundInDBShouldSaveTagInDB() {
        question = questionRepository.findById(1L).get();
        tag = new Tag(999L,"HTML", "For frond-end developers ONLY");

        assertTrue(questionService.addTagToQuestion(tag, question));
        assertThat(question.getTagList().contains(tag));
    }

    @Test
    @DisplayName("Integration test for QuestionService to add tag to question then throws exception")
    public void ifTagAlreadyAddedToQuestionThrowException() {
        injectQuestionAndTagPair(2L, "JAVA");

        assertThatThrownBy(() -> questionService.addTagToQuestion(tag, question))
                .isInstanceOf(TagAlreadyAddedException.class);
    }

    private void injectQuestionAndTagPair(long commentId, String tagName) {
        question = questionRepository.findById(commentId).get();
        tag = tagRepository.findByTagName(tagName).get();
    }

}
