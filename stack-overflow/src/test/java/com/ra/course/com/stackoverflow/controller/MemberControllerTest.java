package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.LogInDto;
import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.dto.UpdateDto;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.exception.service.WrongPasswordException;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.*;
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

    private MemberDto memberDto;
    private UpdateDto updateDto;

    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "Password!111";
    private final String NAME = "Member Name";
    private final Long ID = 1L;
    private final String EXCEPTION_MESSAGE = "Exception message";

    @BeforeEach
    void setUp() {
        memberDto = mockMemberDto();
        updateDto = new UpdateDto(ID,  "New Name", "New_password111");
    }

//    Test url = /member
    @Test
    public void whenGetViewProfileThenReturnProfileTemplate() throws Exception {
        mockMvc.perform(get(MEMBER_URL)
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name(PROFILE_VIEW)));

    }

// Test url = member/update
    @Test
    public void whenGetUpdateWithSessionMemberThenReturnTemplate() throws Exception {
        mockMvc.perform(get(MEMBER_URL + UPDATE_URL)
        .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("updateDto"),
                        view().name(UPDATE_VIEW)));

    }

    @Test
    public void whenPostUpdateThenSessionAttrChanged() throws Exception {
        //given
        var newMemberDto = mockMemberDto();
        newMemberDto.setName("New Name");
        when(service.updateMember(updateDto, PASSWORD)).thenReturn(newMemberDto);
        //when
        var response = mockMvc.perform(post(MEMBER_URL + UPDATE_URL)
                .params(mapForParams(PASSWORD, updateDto.getPassword(), updateDto.getName()))
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn().getResponse();
        //then
        assertEquals(MEMBER_URL + LOGOUT_URL, response.getRedirectedUrl());
    }
    @Test
    public void whenPostUpdateThrowsMemberNotFoundExceptionThenReturnMainTemplate() throws Exception {
        //given
        when(service.updateMember(updateDto, PASSWORD)).thenThrow(new MemberNotFoundException(EXCEPTION_MESSAGE));
        //then
        mockMvc.perform(post(MEMBER_URL + UPDATE_URL)
                .params(mapForParams(PASSWORD, updateDto.getPassword(), updateDto.getName()))
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isInternalServerError(),
                        view().name(MAIN_VIEW)
                ));
    }
    @Test
    public void whenPostUpdateThrowsBindExceptionThenReturnTemplate() throws Exception {

        mockMvc.perform(post(MEMBER_URL + UPDATE_URL)
                .params(mapForParams(PASSWORD, "PASSWORD", ""))
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("passwordError", "nameError"),
                        view().name(UPDATE_VIEW)
                ));
    }
    @Test
    public void whenPostUpdateThrowsWrongPasswordExceptionThenReturnMainTemplate() throws Exception {
        //given
        when(service.updateMember(updateDto, "PASSWORD")).thenThrow(new WrongPasswordException(EXCEPTION_MESSAGE));
        //then
        mockMvc.perform(post(MEMBER_URL + UPDATE_URL)
                .params(mapForParams("PASSWORD", updateDto.getPassword(), updateDto.getName()))
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists(TEXT_ATTR),
                        view().name(UPDATE_VIEW)
                ));
    }

//    Test url = /member/delete

    @Test
    public void whenPostDeleteThenOk() throws Exception {
        var response = mockMvc.perform(post(MEMBER_URL + DELETE_URL)
                .param("currentPassword", PASSWORD)
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn().getResponse();
        assertEquals(MEMBER_URL + LOGOUT_URL, response.getRedirectedUrl());
        verify(service).deleteMember(ID, PASSWORD);
    }
    @Test
    public void whenPostDeleteThrowsMemberNotFoundExceptionThenReturnMainTemplate() throws Exception {
        //given
        doThrow(new MemberNotFoundException(EXCEPTION_MESSAGE))
                .when(service).deleteMember(ID, PASSWORD);
        //then
        mockMvc.perform(post(MEMBER_URL + DELETE_URL)
                .param("currentPassword", PASSWORD)
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isInternalServerError(),
                        view().name(MAIN_VIEW)
                ));
        verify(service).deleteMember(ID, PASSWORD);
    }

    @Test
    public void whenPostDeleteThrowsWrongPasswordExceptionThenReturnUpdateTemplate() throws Exception {
        //given
        doThrow(new WrongPasswordException(EXCEPTION_MESSAGE))
                .when(service).deleteMember(ID, "PASSWORD");
        //then
        mockMvc.perform(post(MEMBER_URL + DELETE_URL)
                .param("currentPassword", "PASSWORD")
                .sessionAttr(MEMBER_ATTR, memberDto))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists(TEXT_ATTR),
                        view().name(UPDATE_VIEW)
                ));
        verify(service).deleteMember(ID, "PASSWORD");
    }




//    Test url = /member/logout

    @Test
    public void whenLogOutThenReturnMainTemplate() throws Exception {
        //when
        var response = mockMvc.perform(get(MEMBER_URL + LOGOUT_URL))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn().getResponse();
        //then

        assertEquals("/", response.getRedirectedUrl());
    }

    private MemberDto mockMemberDto() {
        return MemberDto.builder()
                .id(1L)
                .name(NAME)
                .email(EMAIL)
                .questions(new ArrayList<>())
                .answers(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    private MultiValueMap<String, String> mapForParams(String currentPassword, String password, String name) {
        return new LinkedMultiValueMap<>() {{
            add("currentPassword", currentPassword);
            add("password", password);
            add("name", name);
        }};
    }

}
