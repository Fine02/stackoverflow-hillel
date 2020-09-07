package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.service.search.SearchQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchQuestionController.class)
public class SearchQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchQuestionService service;

    private List<QuestionDto> expectedList;

    @BeforeEach
    void setUp() {
        expectedList = List.of(getQuestion());

    }

    @Test
    void whenSearchByAuthorId() throws Exception {
        //given
        when(service.searchByAuthorId(1L)).thenReturn(expectedList);
        //then
        mockMvc.perform(get("/search/questions")
                        .param("authorId", "1"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    @Test
    void whenSearchByPhrase() throws Exception {
        //given
        when(service.searchInTitle("phrase")).thenReturn(expectedList);
        //then
        mockMvc.perform(get("/search/questions")
                        .param("phrase", "phrase"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    @Test
    void whenSearchByTag() throws Exception {
        //given
        when(service.searchByTag("tagName")).thenReturn(expectedList);
        //then
        mockMvc.perform(get("/search/questions")
                        .param("tag", "tagName"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    @Test
    void whenSearchByTagAndPhrase() throws Exception {
        //given
        when(service.searchByTitleAndTagName("search", "tagName")).thenReturn(expectedList);
        //then
        mockMvc.perform(get("/search/questions")
                        .params(new LinkedMultiValueMap<>(){{
                            add("tag", "tagName");
                            add("phrase", "search");}}))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    private QuestionDto getQuestion(){
        var question = new QuestionDto();
        question.setId(1L);
        question.setTitle("Title");
        question.setText("Text");
        question.setAuthor(1L);
        return question;
    }
}
