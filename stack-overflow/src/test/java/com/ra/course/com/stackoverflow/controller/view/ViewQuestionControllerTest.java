package com.ra.course.com.stackoverflow.controller.view;

import com.ra.course.com.stackoverflow.dto.post.QuestionFullDto;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(ViewQuestionController.class)
public class ViewQuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    void whenViewQuestionByIdThenReturnQuestionFullDto() throws Exception {
        //given
        final var question = getQuestion();
        when(questionService.getQuestionById(1L)).thenReturn(question);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/view/question/1"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/question"),
                        model().attribute("question", question),
                        model().attributeExists("dto")
                ));
    }

    private QuestionFullDto getQuestion(){
        final var question = new QuestionFullDto();
            question.setId(1L);
            question.setTitle("Title");
            question.setText("Text");
            question.setAuthor(1L);
        return question;
    }
}

