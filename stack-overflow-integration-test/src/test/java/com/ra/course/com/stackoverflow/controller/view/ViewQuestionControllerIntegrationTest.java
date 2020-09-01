package com.ra.course.com.stackoverflow.controller.view;

import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.entity.enums.QuestionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ViewQuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private QuestionFullDto question;

    @BeforeEach
    void setUp() {
        question = new QuestionFullDto();
            question.setId(1L);
            question.setTitle("First question title");
            question.setText("First question description");
            question.setAuthor(1L);
            question.setStatus(QuestionStatus.OPEN);
    }

    @Test
    void whenViewQuestionById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/view/question/1"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/question"),
                        model().attributeExists("dto"),
                        model().attribute("question", question)
                ));

    }

}
