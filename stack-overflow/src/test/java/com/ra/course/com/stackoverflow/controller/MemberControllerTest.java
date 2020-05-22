package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
import com.ra.course.com.stackoverflow.exception.service.LoginMemberException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
    private RegisterDto registerDto;
    private MemberDto memberDto;
    private UpdateDto updateDto;

    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "Password!111";
    private final String NAME = "Member Name";
    private final Long ID = 1L;
    private final String EXCEPTION_MESSAGE = "Exception message";
    private final String TEXT_ATTR = "text";
    private final String MEMBER_DTO_NAME = "memberDto";

    private final String REGISTER_TEMPLATE = "member/register";
    private final String PROFILE_TEMPLATE = "member/profile";
    private final String LOGIN_TEMPLATE = "member/login";
    private final String SEARCH_TEMPLATE = "member/member-search";
    private final String VIEW_TEMPLATE = "member/view-members";
    private final String UPDATE_TEMPLATE = "member/update";
    private final String MAIN_TEMPLATE = "main";

    final String REGISTER_URL = "/member/register";
    final String LOGIN_URL = "/member/login";
    final String PROFILE_URL = "/member/profile";
    final String SEARCH_URL = "/member/search";
    final String UPDATE_URL = "/member/update";
    final String DELETE_URL = "/member/delete";

    @BeforeEach
    void setUp() {
        memberDto = mockMemberDto();
        logInDto = new LogInDto(EMAIL, PASSWORD);
        registerDto = new RegisterDto(NAME, EMAIL, PASSWORD);
        updateDto = new UpdateDto(null,  "New Name", "New_password111");
    }

//    Test url = /member/login

    @Test
    public void whenGetLoginThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get(LOGIN_URL))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("logInDto"),
                        view().name(LOGIN_TEMPLATE)));
    }

    @Test
    public void whenPostInvalidLogInDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams("invalid email", " ", null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("emailError", "passwordError"),
                        view().name(LOGIN_TEMPLATE)));
    }

    @Test
    public void whenPostLoginThenThrowsLoginException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new LoginMemberException(EXCEPTION_MESSAGE));
        //when
        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD, null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, EXCEPTION_MESSAGE),
                        view().name(LOGIN_TEMPLATE)));
    }

    @Test
    public void whenPostLoginThenThrowsWrongPasswordException() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenThrow(new WrongPasswordException(EXCEPTION_MESSAGE));
        //when
        mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD, null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, EXCEPTION_MESSAGE),
                        view().name(LOGIN_TEMPLATE)));
    }

    @Test
    public void whenPostLoginThenSetSessionAttributeMemberDto() throws Exception {
        //given
        when(service.loginMember(logInDto)).thenReturn(memberDto);
        //when
        HttpSession session = mockMvc.perform(post(LOGIN_URL)
                .params(mapForParams(EMAIL, PASSWORD, null)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(PROFILE_TEMPLATE)))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(memberDto, session.getAttribute(MEMBER_DTO_NAME));
    }
    
//    Test url = member/profile

    @Test
    public void whenGetViewProfileAndSessionMemberAbsentThenReturnMainTemplate() throws Exception {
        mockMvc.perform(get(PROFILE_URL))
                .andDo(print())
                .andExpect(matchAll(
                        status().isUnauthorized(),
                        view().name(MAIN_TEMPLATE)));

    }

    @Test
    public void whenGetViewProfileThenReturnProfileTemplate() throws Exception {
        mockMvc.perform(get(PROFILE_URL)
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(PROFILE_TEMPLATE)));

    }

//    Test url = member/register

    @Test
    public void whenGetRegisterThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get(REGISTER_URL))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("registerDto"),
                        view().name(REGISTER_TEMPLATE)));
    }

    @Test
    public void whenPostInvalidRegisterDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post(REGISTER_URL)
                .params(mapForParams("invalid email", "   ", "0")))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("emailError", "passwordError", "nameError"),
                        view().name(REGISTER_TEMPLATE)));
    }

    @Test
    public void whenPostRegisterThenThrowsAlreadyExistAccountException() throws Exception {
        //given
        when(service.registerMember(registerDto)).thenThrow(new AlreadyExistAccountException(EXCEPTION_MESSAGE));
        //when
        mockMvc.perform(post(REGISTER_URL)
                .params(mapForParams(EMAIL, PASSWORD, NAME)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, EXCEPTION_MESSAGE),
                        view().name(REGISTER_TEMPLATE)));
    }

    @Test
    public void whenPostRegisterThenSetSessionAttributeMemberDto() throws Exception {
        //given
        when(service.registerMember(registerDto)).thenReturn(memberDto);
        //when
        HttpSession session = mockMvc.perform(post(REGISTER_URL)
                .params(mapForParams(EMAIL, PASSWORD, NAME)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("done"),
                        view().name(PROFILE_TEMPLATE)))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(memberDto, session.getAttribute("memberDto"));
    }

    @Test
    public void whenGetUpdateAndSessionMemberAbsentThenReturnMainTemplate() throws Exception {
        mockMvc.perform(get(UPDATE_URL))
                .andDo(print())
                .andExpect(matchAll(
                        status().isUnauthorized(),
                        view().name(MAIN_TEMPLATE)));

    }
    @Test
    public void whenGetUpdateWithSessionMemberThenReturnTemplate() throws Exception {
        mockMvc.perform(get(UPDATE_URL)
        .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("updateDto"),
                        view().name(UPDATE_TEMPLATE)));

    }
    @Test
    public void whenGetDeleteAndSessionMemberAbsentThenReturnMainTemplate() throws Exception {
        mockMvc.perform(get(DELETE_URL))
                .andDo(print())
                .andExpect(matchAll(
                        status().isUnauthorized(),
                        view().name(MAIN_TEMPLATE)));

    }
    @Test
    public void whenGetDeleteWithSessionMemberThenReturnTemplate() throws Exception {
        mockMvc.perform(get(DELETE_URL)
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(UPDATE_TEMPLATE)));

    }

    @Test
    public void whenPostUpdateThenOk() throws Exception {
        mockMvc.perform(post(UPDATE_URL)
                .param("currentPassword", PASSWORD)
                .params(mapForParams(null, "New password111", "New Name"))
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(PROFILE_TEMPLATE)
                ));
    }
    @Test
    public void whenPostUpdateThrowsMemberNotFoundExceptionThenReturnMainTemplate() throws Exception {
        //given
        updateDto.setId(ID);
        when(service.updateMember(updateDto, PASSWORD)).thenThrow(new MemberNotFoundException(EXCEPTION_MESSAGE));
        //then
        mockMvc.perform(post(UPDATE_URL)
                .param("currentPassword", PASSWORD)
                .params(mapForParams(null, updateDto.getPassword(), updateDto.getName()))
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isInternalServerError(),
                        view().name(MAIN_TEMPLATE)
                ));
    }
    @Test
    public void whenPostUpdateThrowsBindExceptionThenReturnTemplate() throws Exception {

        mockMvc.perform(post(UPDATE_URL)
                .param("currentPassword", PASSWORD)
                .params(mapForParams(null, "PASSWORD", updateDto.getName()))
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("passwordError"),
                        view().name(UPDATE_TEMPLATE)
                ));
    }
    @Test
    public void whenPostUpdateThrowsWrongPasswordExceptionThenReturnMainTemplate() throws Exception {
        //given
        updateDto.setId(ID);
        when(service.updateMember(updateDto, "PASSWORD")).thenThrow(new WrongPasswordException(EXCEPTION_MESSAGE));
        //then
        mockMvc.perform(post(UPDATE_URL)
                .param("currentPassword", "PASSWORD")
                .params(mapForParams(null, updateDto.getPassword(), updateDto.getName()))
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists(TEXT_ATTR),
                        view().name(UPDATE_TEMPLATE)
                ));
    }

    @Test
    public void whenPostDeleteThenOk() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post(DELETE_URL)
                .param("currentPassword", PASSWORD)
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound()
                )).andReturn().getResponse();
        assertEquals("/member/exit", response.getRedirectedUrl());
        verify(service).deleteMember(ID, PASSWORD);
    }
    @Test
    public void whenPostDeleteThrowsMemberNotFoundExceptionThenReturnMainTemplate() throws Exception {
        //given
        doThrow(new MemberNotFoundException(EXCEPTION_MESSAGE))
                .when(service).deleteMember(ID, PASSWORD);
        //then
        mockMvc.perform(post(DELETE_URL)
                .param("currentPassword", PASSWORD)
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isInternalServerError(),
                        view().name(MAIN_TEMPLATE)
                ));
        verify(service).deleteMember(ID, PASSWORD);
    }

    @Test
    public void whenDeleteThrowsWrongPasswordExceptionThenReturnMainTemplate() throws Exception {
        //given
        doThrow(new WrongPasswordException(EXCEPTION_MESSAGE))
                .when(service).deleteMember(ID, "PASSWORD");
        //then
        mockMvc.perform(post(DELETE_URL)
                .param("currentPassword", "PASSWORD")
                .sessionAttr(MEMBER_DTO_NAME, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists(TEXT_ATTR),
                        view().name(UPDATE_TEMPLATE)
                ));
        verify(service).deleteMember(ID, "PASSWORD");
    }

//    Test url = /member/search

    @Test
    public void whenGetSearchThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get(SEARCH_URL))
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(SEARCH_TEMPLATE)));
    }

    @Test
    public void whenPostSearchMemberThenReturnListOfMembersWithView() throws Exception {
        //given
        var memberList = new ArrayList<>(List.of(memberDto, memberDto, memberDto));
        when(service.findByMemberName(NAME)).thenReturn(memberList);
        //then
        mockMvc.perform(post(SEARCH_URL)
                .param("name", NAME))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attribute("viewMembers", memberList),
                        view().name(VIEW_TEMPLATE)));
    }


//    Test url = /member/exit

    @Test
    public void whenExitThemReturnSessionWithoutMemberDto() throws Exception {
        //when
        var response = mockMvc.perform(get("/member/exit"))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn().getResponse();
        //then
        assertEquals("/", response.getRedirectedUrl());
    }

    private MemberDto mockMemberDto() {
        return MemberDto.builder()
                .id(1L)
                .name("Member name")
                .email("email@gmail.com")
                .questions(new ArrayList<>())
                .answers(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private MultiValueMap<String, String> mapForParams(String email, String password, String name) {
        return new LinkedMultiValueMap<>() {{
            add("email", email);
            add("password", password);
            add("name", name);
        }};
    }

}
