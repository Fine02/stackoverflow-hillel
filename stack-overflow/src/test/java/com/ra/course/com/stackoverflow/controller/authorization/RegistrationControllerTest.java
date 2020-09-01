package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.RegisterDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.exception.service.AlreadyExistAccountException;
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

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService service;

    private RegisterDto registerDto;

    @BeforeEach
    void setUp() {
        registerDto = new RegisterDto("Member Name", "email@gmail.com", "Password!111");
    }

    @Test
    public void whenGetRegisterThenReturnModelWithTemplate() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("dto"),
                        view().name("authorization/registration")));
    }

    @Test
    public void whenPostInvalidRegisterDataThenThrowsBindException() throws Exception {

        mockMvc.perform(post("/registration")
                .params(new LinkedMultiValueMap<>(){{
                    add("email", "invalid email");
                    add("password", "invalid password");
                    add("name", "  "); }}))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/registration"),
                        flash().attributeExists("emailError", "passwordError", "nameError")));
    }

    @Test
    public void whenPostRegisterThenThrowsAlreadyExistAccountException() throws Exception {
        //given
        when(service.registerMember(registerDto)).thenThrow(new AlreadyExistAccountException("exception"));
        //when
        mockMvc.perform(post("/registration")
                .params(validParams()))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        flash().attribute("text", "exception"),
                        redirectedUrl("/registration")));
    }

    @Test
    public void whenPostRegisterThenSetSessionAttributeAccount() throws Exception {
        //given
        final var sessionMember = new SessionMemberDto();
            sessionMember.setId(1L);
            sessionMember.setName("Member name");
            sessionMember.setRole(AccountRole.USER);
        when(service.registerMember(registerDto)).thenReturn(sessionMember);
        //when
        HttpSession session = mockMvc.perform(post("/registration")
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
            add("name", "Member Name");
        }};
    }
}
