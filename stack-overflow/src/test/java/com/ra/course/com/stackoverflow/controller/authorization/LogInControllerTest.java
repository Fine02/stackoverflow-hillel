package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.LogInDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;

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
    private MemberService service;

    private LogInDto logInDto;

    @BeforeEach
    void setUp() {
        logInDto = new LogInDto();
            logInDto.setEmail("email@gmail.com");
            logInDto.setPassword("Password!111");
    }

    @Test
    public void whenGetLoginThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("dto"),
                        view().name("authorization/login")));
    }

    @Test
    public void whenPostInvalidLogInDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post("/login")
                .params(new LinkedMultiValueMap<>() {{
                    add("email", "invalid email");
                    add("password", " "); }}))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/login"),
                        flash().attributeExists("emailError", "passwordError")));
    }

    @Test
    public void whenPostLoginThenThrowsLoginException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new LoginMemberException("exception"));
        //when
        mockMvc.perform(post("/login")
                .params(validParams()))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/login"),
                        flash().attributeExists("text")));
    }

    @Test
    public void whenPostLoginThenThrowsWrongPasswordException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new WrongPasswordException("exception"));
        //when
        mockMvc.perform(post("/login")
                .params(validParams()))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/login"),
                        flash().attributeExists("text")));
    }

    @Test
    public void whenPostLoginThenSetSessionAttributeAccount() throws Exception {
        //given
        final var sessionMember = new SessionMemberDto();
            sessionMember.setId(1L);
            sessionMember.setName("Member name");
            sessionMember.setRole(AccountRole.USER);
        when(service.loginMember(logInDto)).thenReturn(sessionMember);
        //when
        final HttpSession session = mockMvc.perform(post("/login")
                .params(validParams()))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/profile")))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(sessionMember, session.getAttribute("account"));
    }

    private MultiValueMap<String, String> validParams() {
        return new LinkedMultiValueMap<>() {{
            add("email", "email@gmail.com");
            add("password", "Password!111");
        }};
    }
}
