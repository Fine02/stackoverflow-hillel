package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.service.member.MemberService;
import com.ra.course.com.stackoverflow.service.post.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getMemberDto;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getQuestionFullDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViewController.class)
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private MemberService memberService;

    @Test
    void whenViewQuestionByIdThenReturnQuestionFullDto() throws Exception {
        //given
        var question = getQuestionFullDto();
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
    @Test
    void whenViewQuestionByIdThenReturnMemberDto() throws Exception {
        //given
        var member = getMemberDto();
        when(memberService.getMemberById(1L)).thenReturn(member);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/view/member/1"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/member"),
                        model().attribute("member", member)
                ));
    }
}
