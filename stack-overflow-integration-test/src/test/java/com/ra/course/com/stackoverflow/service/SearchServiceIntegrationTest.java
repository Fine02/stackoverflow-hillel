package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.Tag;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.search.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SearchServiceIntegrationTest {

    @Autowired
    private SearchService searchService;

    private final String TAG_NAME = "JAVA";
    private final String SEARCH_TEXT = "Another";
    private List<Question> expectedResult;

    @BeforeEach
    void setUp() {
        expectedResult = new ArrayList<>(Collections.singletonList(createQuestion()));
    }

    @Test
    @DisplayName("Integration test for SearchService to get questions if tag is null")
    public void shouldReturnEmptyListIfTagByNameNull() {
        assertThat(searchService.searchByTag(null)).isEmpty();
    }

    @Test
    @DisplayName("Integration test for SearchService to get questions if search phrase is null")
    public void shouldReturnEmptyListIfSearchPhraseIsNull() {
        assertThat(searchService.searchInTitle(null)).isEmpty();
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by tagname")
    public void shouldFindQuestionByTag() {
        //when
        List<Question> actualResult = searchService.searchByTag(TAG_NAME);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by search text")
    public void shouldFindQuestionByTextInTitle() {
        //when
        List<Question> actualResult = searchService.searchInTitle(SEARCH_TEXT);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by tag and search text")
    public void shouldFindQuestionByTextAndTag() {
        //when
        List<Question> actualResult = searchService.searchInTitleByTag(SEARCH_TEXT, TAG_NAME);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);

    }

    private Question createQuestion() {
        return Question.builder()
                .id(2L)
                .title("Another question title")
                .authorId(1L)
                .build();
    }

}
