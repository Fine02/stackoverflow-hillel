package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LogInControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenLoginWithInvalidParamsThenReturnToLoginPage() throws Exception {

        mockMvc.perform(post("/login")
                .param("email", "email1@gmail.com")
                .param("password", "invalid password"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/login"),
                        flash().attributeExists("text")));
    }

    @Test
    public void whenLoginThenSetSessionAttributeAccount() throws Exception {

        final var member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);

        final var session = mockMvc.perform(post("/login")
                .param("email", "email1@gmail.com")
                .param("password", "password1"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/profile")))
                .andReturn().getRequest().getSession();

        assertEquals(member, session.getAttribute("account"));
    }
}
