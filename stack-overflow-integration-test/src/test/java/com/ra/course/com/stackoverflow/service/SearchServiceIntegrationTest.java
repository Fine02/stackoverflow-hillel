package com.ra.course.com.stackoverflow.service;

import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.repository.QuestionRepository;
import com.ra.course.com.stackoverflow.service.search.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SearchServiceIntegrationTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SearchService searchService;
    private final String TAG_NAME = "JAVA";
    private final String SEARCH_TEXT = "Another";
    private List<Question> expectedResult;

    @BeforeEach
    void setUp() {
        expectedResult = List.of(questionRepository.findById(2L).get());
    }

    @Test
    @DisplayName("Integration test for SearchService to get questions if tag/searchphrase null")
    public void shouldReturnEmptyListIfTagByNameNull() {
        assertThat(searchService.searchByTag(null)).isEmpty();
        assertThat(searchService.searchInTitle(null)).isEmpty();
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by tagname")
    public void shouldFindQuestionByTag() {
        assertThat(searchService.searchByTag(TAG_NAME)).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by search text")
    public void shouldFindQuestionByTextInTitle() {
        assertThat(searchService.searchInTitle(SEARCH_TEXT)).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Integration test for SearchService when search questions by tag and search text")
    public void shouldFindQuestionByTextAndTag() {
        assertThat(searchService.searchInTitleByTag(SEARCH_TEXT, TAG_NAME)).isEqualTo(expectedResult);

    }
}
