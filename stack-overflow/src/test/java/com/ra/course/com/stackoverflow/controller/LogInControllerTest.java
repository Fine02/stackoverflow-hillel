package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBER_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LogInController.class)
public class LogInControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberStorageService service;

    private LogInDto logInDto;
    private MemberDto memberDto;

    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "Password!111";
    private final String EXCEPTION_MESSAGE = "Exception message";

    @BeforeEach
    void setUp() {
        memberDto = mockMemberDto();
        logInDto = new LogInDto(EMAIL, PASSWORD);
    }

    @Test
    public void whenGetLoginThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("logInDto"),
                        view().name(LOGIN_VIEW)));
    }

    @Test
    public void whenPostInvalidLogInDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams("invalid email", " ")))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("emailError", "passwordError"),
                        view().name(LOGIN_VIEW)));
    }

    @Test
    public void whenPostLoginThenThrowsLoginException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new LoginMemberException(EXCEPTION_MESSAGE));
        //when
        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, EXCEPTION_MESSAGE),
                        view().name(LOGIN_VIEW)));
    }

    @Test
    public void whenPostLoginThenThrowsWrongPasswordException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new WrongPasswordException(EXCEPTION_MESSAGE));
        //when
        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, EXCEPTION_MESSAGE),
                        view().name(LOGIN_VIEW)));
    }

    @Test
    public void whenPostLoginThenSetSessionAttributeMemberDto() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenReturn(memberDto);
        //when
        HttpSession session = mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isAccepted(),
                        view().name(PROFILE_VIEW)))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(memberDto, session.getAttribute(MEMBER_ATTR));
    }


    private MemberDto mockMemberDto() {
        return MemberDto.builder()
                .id(1L)
                .name("Member Name")
                .email(EMAIL)
                .questions(new ArrayList<>())
                .answers(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private MultiValueMap<String, String> mapForParams(String email, String password) {
        return new LinkedMultiValueMap<>() {{
            add("email", email);
            add("password", password);
        }};
    }
}
