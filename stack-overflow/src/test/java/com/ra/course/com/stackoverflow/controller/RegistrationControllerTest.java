package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.dto.RegisterDto;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberStorageService service;

    private RegisterDto registerDto;
    private MemberDto memberDto;

    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "Password!111";
    private final String NAME = "Member Name";

    @BeforeEach
    void setUp() {
        memberDto = mockMemberDto();
        registerDto = new RegisterDto(NAME, EMAIL, PASSWORD);
    }

    @Test
    public void whenGetRegisterThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get(REGISTER_URL))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("registerDto"),
                        view().name(REGISTER_VIEW)));
    }

    @Test
    public void whenPostInvalidRegisterDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post(REGISTER_URL)
                .params(mapForParams("invalid email", "   ", "0")))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attributeExists("emailError", "passwordError", "nameError"),
                        view().name(REGISTER_VIEW)));
    }

    @Test
    public void whenPostRegisterThenThrowsAlreadyExistAccountException() throws Exception {
        //given
        var exceptionMessage = "Exception message";
        when(service.registerMember(registerDto)).thenThrow(new AlreadyExistAccountException(exceptionMessage));
        //when
        mockMvc.perform(post(REGISTER_URL)
                .params(mapForParams(EMAIL, PASSWORD, NAME)))
                .andDo(print())
                .andExpect(matchAll(
                        status().isBadRequest(),
                        model().attribute(TEXT_ATTR, exceptionMessage),
                        view().name(REGISTER_VIEW)));
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
                        status().isCreated(),
                        model().attributeExists("done"),
                        view().name(PROFILE_VIEW)))
                .andReturn().getRequest().getSession();
        //then
        assertEquals(memberDto, session.getAttribute(MEMBER_ATTR));
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

    private MultiValueMap<String, String> mapForParams(String email, String password, String name) {
        return new LinkedMultiValueMap<>() {{
            add("email", email);
            add("password", password);
            add("name", name);
        }};
    }
}
