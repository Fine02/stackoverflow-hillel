//package com.ra.course.com.stackoverflow.service;
//
//import com.ra.course.com.stackoverflow.dto.question.QuestionDto;
//import com.ra.course.com.stackoverflow.entity.Question;
//import com.ra.course.com.stackoverflow.service.search.SearchQuestionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class SearchQuestionServiceIntegrationTest {
//
//    @Autowired
//    private SearchQuestionService searchQuestionService;
//
//    private final String TAG_NAME = "JAVA";
//    private final String SEARCH_TEXT = "Another";
//    private List<Question> expectedResult;
//
//    @BeforeEach
//    void setUp() {
//        expectedResult = new ArrayList<>(Collections.singletonList(createQuestion()));
//    }
//
//    @Test
//    @DisplayName("Integration test for SearchService to get questions if tag is null")
//    public void shouldReturnEmptyListIfTagByNameNull() {
//        assertThat(searchQuestionService.searchByTag(null)).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Integration test for SearchService to get questions if search phrase is null")
//    public void shouldReturnEmptyListIfSearchPhraseIsNull() {
//        assertThat(searchQuestionService.searchInTitle(null)).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Integration test for SearchService when search questions by tagname")
//    public void shouldFindQuestionByTag() {
//        //when
//        List<QuestionDto> actualResult = searchQuestionService.searchByTag(TAG_NAME);
//
//        //then
//        assertThat(actualResult).isEqualTo(expectedResult);
//    }
//
//    @Test
//    @DisplayName("Integration test for SearchService when search questions by search text")
//    public void shouldFindQuestionByTextInTitle() {
//        //when
//        List<QuestionDto> actualResult = searchQuestionService.searchInTitle(SEARCH_TEXT);
//
//        //then
//        assertThat(actualResult).isEqualTo(expectedResult);
//    }
//
//    @Test
//    @DisplayName("Integration test for SearchService when search questions by tag and search text")
//    public void shouldFindQuestionByTextAndTag() {
//        //when
//        List<QuestionDto> actualResult = searchQuestionService.searchByTitleAndTagName(SEARCH_TEXT, TAG_NAME);
//
//        //then
//        assertThat(actualResult).isEqualTo(expectedResult);
//
//    }
//
//    private Question createQuestion() {
//        return Question.builder()
//                .id(2L)
//                .title("Another question title")
//                .author(1L)
//                .build();
//    }
//
//}
