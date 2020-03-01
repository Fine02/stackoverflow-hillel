package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.exception.service.TagNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.QuestionRepository;
import com.ra.course.com.stackoverflow.repository.interfaces.TagRepository;
import com.ra.course.com.stackoverflow.service.implementation.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class SearchServiceTest {

    private QuestionRepository questionRepository;
    private TagRepository tagRepository;
    private final String TAG_NAME = "testTag";
    private final String SEARCH_TEXT = "searchTest";
    private long id = 1L;
    private String description = "description";
    private SearchService searchService;
    private List<Question> expectedResult;


    private Account account = Account.builder()
                                     .password("password")
                                     .email("email")
                                     .name("name")
                                     .build();

    private Member member = Member.builder()
                                  .id(id)
                                  .account(account)
                                  .build();

    private Tag tag = new Tag(id, TAG_NAME, description, List.of(), 1, 1);

    private Question question = Question.builder()
                                        .id(id)
                                        .description(description)
                                        .title("title")
                                        .author(member)
                                        .tagList(List.of(tag))
                                        .build();

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        tagRepository = mock(TagRepository.class);
        searchService = new SearchServiceImpl(questionRepository, tagRepository);
        expectedResult = List.of(question);
    }

    @Test
    public void shouldThrowExceptionIfTagByNameNotFound() {
        when(tagRepository.findByTagName(TAG_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> searchService.searchByTag(TAG_NAME)).isInstanceOf(TagNotFoundException.class)
                                                                     .hasMessage("There is no Tag in DB like: " + TAG_NAME);

        verify(tagRepository).findByTagName(TAG_NAME);
        verifyNoMoreInteractions(tagRepository);
    }

    @Test
    public void shouldThrowExceptionIfTagByNameNull() {
        assertThatThrownBy(() -> searchService.searchByTag(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tag name is null");

        verifyNoInteractions(tagRepository, questionRepository);
    }

    @Test
    public void shouldFindQuestionByTag() throws TagNotFoundException {
        when(tagRepository.findByTagName(TAG_NAME)).thenReturn(Optional.of(tag));
        when(questionRepository.findByTag(tag)).thenReturn(expectedResult);

        List<Question> actualResponse = searchService.searchByTag(TAG_NAME);

        assertThat(actualResponse).isEqualTo(expectedResult);

        verify(tagRepository).findByTagName(TAG_NAME);
        verify(questionRepository).findByTag(tag);
        verifyNoMoreInteractions(tagRepository, questionRepository);
    }

    @Test
    public void shouldThrowExceptionIfSearchPhraseIsNull() {
        assertThatThrownBy(() -> searchService.searchInTitle(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Search phrase is null");

        verifyNoInteractions(tagRepository, questionRepository);
    }

    @Test
    public void shouldFindQuestionByTextInTitle() {
        when(questionRepository.findByTitle(SEARCH_TEXT)).thenReturn(expectedResult);

        List<Question> actualResponse = searchService.searchInTitle(SEARCH_TEXT);

        assertThat(actualResponse).isEqualTo(expectedResult);
        verify(questionRepository).findByTitle(SEARCH_TEXT);
        verifyNoMoreInteractions(questionRepository);
    }

    @Test
    public void shouldFindQuestionByTextAndTag() throws TagNotFoundException {

        when(tagRepository.findByTagName(TAG_NAME)).thenReturn(Optional.of(tag));
        when(questionRepository.findByTitleAndTag(SEARCH_TEXT, tag)).thenReturn(expectedResult);

        List<Question> actualResponse = searchService.searchInTitleByTag(SEARCH_TEXT, TAG_NAME);

        assertThat(actualResponse).isEqualTo(expectedResult);

        verify(questionRepository).findByTitleAndTag(SEARCH_TEXT, tag);
        verify(tagRepository).findByTagName(TAG_NAME);
        verifyNoMoreInteractions(questionRepository, tagRepository);
    }

    @Test
    public void shouldThrowExceptionIfTagByNameNotFoundInSearchInTitleByTag() {
        when(tagRepository.findByTagName(TAG_NAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> searchService.searchInTitleByTag(SEARCH_TEXT, TAG_NAME)).isInstanceOf(TagNotFoundException.class)
                                                                                         .hasMessage("There is no Tag in DB like: " + TAG_NAME);

        verify(tagRepository).findByTagName(TAG_NAME);
        verifyNoMoreInteractions(tagRepository);
    }

    @Test
    public void shouldThrowExceptionIfSearchPhraseIsNullButTagIsValid() {
        assertThatThrownBy(() -> searchService.searchInTitleByTag(null, TAG_NAME))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(tagRepository, questionRepository);
    }

    @Test
    public void shouldThrowExceptionIfSearchPhraseIsValidButTagIsNull() {
        assertThatThrownBy(() -> searchService.searchInTitleByTag(SEARCH_TEXT, null))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(tagRepository, questionRepository);
    }
}