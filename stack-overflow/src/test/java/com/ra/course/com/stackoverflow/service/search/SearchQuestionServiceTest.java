package com.ra.course.com.stackoverflow.service.search;

import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.search.impl.SearchQuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchQuestionServiceTest {

    private static SearchQuestionService service;

    private QuestionDto questionDto;
    private Tag tag;
    private  Question question;

    //mock inner classes
    private final RepositoryUtils utils = mock(RepositoryUtils.class);
    private final QuestionRepository questionData = mock(QuestionRepository.class);

    @BeforeEach
    void setUp() {
        service = new SearchQuestionServiceImpl(questionData, utils);

        question = getQuestion();
        questionDto = getQuestionDto();
        tag = getTag();

        question.setTags(List.of(tag));
        questionDto.setTags(List.of(getTagDto()));

        when(utils.getTagFromDBByTagName(TITLE)).thenReturn(tag);
    }

    @Test
    void whenSearchByTagThenReturnListWithQuestions() {
        //given
        when(questionData.findByTag(tag)).thenReturn(List.of(question));
        //when
        var result = service.searchByTag(TITLE);
        //then
        assertTrue(result.contains(questionDto));
    }

    @Test
    void whenSearchInTitleThenReturnListWithQuestion() {
        //given
        when(questionData.findByTitle(TITLE.toLowerCase())).thenReturn(List.of(question));
        //when
        var result = service.searchInTitle(TITLE);
        //then
        assertTrue(result.contains(questionDto));
    }

    @Test
    void whenSearchInTitleAndTagThenReturnListWithQuestion() {
        //given
        when(questionData.findByTitleAndTag(TITLE.toLowerCase(), tag)).thenReturn(List.of(question));
        //when
        var result = service.searchByTitleAndTagName(TITLE, TITLE);
        //then
        assertTrue(result.contains(questionDto));
    }

    @Test
    void whenGetByAuthorIdThenReturnListWithQuestion() {
        //given
        when(questionData.findByMemberId(ID)).thenReturn(List.of(question));
        //when
        var result = service.searchByAuthorId(ID);
        //then
        assertTrue(result.contains(questionDto));
    }

    @Test
    void whenAnySearchReturnEmptyList() {
        //given
        when(questionData.findByMemberId(ID)).thenReturn(new ArrayList<>());
        when(questionData.findByTitle(TITLE)).thenReturn(new ArrayList<>());
        when(questionData.findByTitleAndTag(TITLE, tag)).thenReturn(new ArrayList<>());
        when(questionData.findByTag(tag)).thenReturn(new ArrayList<>());
        var emptyList = new ArrayList<>();
        //then
        assertThat(emptyList)
                .isEqualTo(service.searchByAuthorId(ID))
                .isEqualTo(service.searchByTag(TITLE))
                .isEqualTo(service.searchByTitleAndTagName(TITLE, TITLE))
                .isEqualTo(service.searchByAuthorId(ID));
    }

}
