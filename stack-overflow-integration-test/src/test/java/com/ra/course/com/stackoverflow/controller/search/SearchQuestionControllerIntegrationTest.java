package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.dto.TagDto;
import com.ra.course.com.stackoverflow.dto.post.QuestionDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SearchQuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private List<QuestionDto> expectedList;

    @BeforeEach
    void setUp() {
        final var tag = new TagDto();
            tag.setName("C#");

        final var question = new QuestionDto();
            question.setId(1L);
            question.setTitle("First question title");
            question.setText("First question description");
            question.setAuthor(1L);
            question.setStatus(QuestionStatus.OPEN);
            question.setTags(List.of(tag));

        expectedList = List.of(question);
    }

    @Test
    void whenSearchByAuthorId() throws Exception {

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

        mockMvc.perform(get("/search/questions")
                        .param("phrase", "First"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    @Test
    void whenSearchByTag() throws Exception {

        mockMvc.perform(get("/search/questions")
                        .param("tag", "C#"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }

    @Test
    void whenSearchByTagAndPhrase() throws Exception {

        mockMvc.perform(get("/search/questions")
                        .params(new LinkedMultiValueMap<>(){{
                            add("tag", "C#");
                            add("phrase", "First");}}))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-questions"),
                        model().attribute("questions", expectedList)
                ));
    }
}
