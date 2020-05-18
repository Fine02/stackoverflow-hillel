package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberStorageService service;

    private LogInDto logInDto;
    private MemberDto memberDto;
    private Long ID = 1L;

    @BeforeEach
    void setUp() {
        memberDto = mockMemberDto();
    }

    @Test
    public void whenGetLoginThenReturnModelWithTemplate() throws Exception{
        mockMvc.perform(get("/member/login"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("logInDto"),
                        view().name("member/login")));
    }
    @Test
    public void whenGetRegisterThenReturnModelWithTemplate() throws Exception{
        mockMvc.perform(get("/member/register"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("registerDto"),
                        view().name("member/register")));
    }
    @Test
    public void whenPostLoginThenSetSessionAttributeMemberDto() throws Exception{
        //given
        logInDto = new LogInDto("email@gmail.com", "Password!111");
        when(service.loginMember(logInDto)).thenReturn(memberDto);
        //when
        HttpSession session = mockMvc.perform(post("/member/login")
                .params(mapForParams("email@gmail.com", "Password!111", null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attribute("memberDto", this.memberDto),
                        view().name("member/greeting")))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(memberDto, session.getAttribute("memberDto"));
    }
    @Test
    public void whenPostLoginThenThrowsLoginException() throws Exception{
        //given
        logInDto = new LogInDto("email@gmail.com", "Password!111");
        when(service.loginMember(logInDto)).thenThrow(new LoginMemberException("No account with email " + logInDto.getEmail()));
        //when
        mockMvc.perform(post("/member/login")
                .params(mapForParams("email@gmail.com", "Password!111", null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute("text", "No account with email " + logInDto.getEmail()),
                        view().name("member/login")));
    }

    @Test
    public void whenInvalidLogInDataThenThrowsException() throws Exception{

        mockMvc.perform(post("/member/login")
        .params(mapForParams("invalid email", " ", null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("emailError", "passwordError"),
                        view().name("member/login")));
    }

    @Test
    public void whenViewMemberThenReturnViewWithMemberDto() throws Exception{
        //given
        when(service.findMemberById(ID)).thenReturn(memberDto);
        var memberList = new ArrayList<>(List.of(memberDto));
        //when
        mockMvc.perform(get("/member/" + ID))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attribute("viewMembers", memberList),
                        view().name("member/view-members")
                ));
    }

    @Test
    public void whenExitThemReturnSessionWithoutMemberDto() throws Exception{
        //when
        var session = mockMvc.perform(get("/member/exit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andReturn().getRequest().getSession();
        //then
        assertNull(session.getAttribute("memberDto"));
    }

    private MemberDto mockMemberDto(){
        return MemberDto.builder()
                .id(1L)
                .name("Member name")
                .email("email@gmail.com")
                .build();
    }

    private MultiValueMap<String, String> mapForParams(String email, String password, String name){
        return  new LinkedMultiValueMap<>() {{
            add("email", email);
            add("password", password);
            add("name", name);
        }};
    }

}
