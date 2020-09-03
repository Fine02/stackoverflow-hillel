package com.ra.course.com.stackoverflow.controller.authorization;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRegisterWithAlreadyExistEmailThenReturnRegisterPage() throws Exception {

        mockMvc.perform(post("/registration")
                .param("email", "email1@gmail.com")
                .param("name", "Some name")
                .param("password", "Some valid password1!"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        flash().attributeExists("text"),
                        redirectedUrl("/registration")));
    }

    @Test
    @Rollback
    @Transactional
    public void whenPostRegisterThenSetSessionAttributeAccount() throws Exception {

        var member = new SessionMemberDto();
            member.setId(4L);
            member.setName("name4");
            member.setRole(AccountRole.USER);

        var session = mockMvc.perform(post("/registration")
                .param("email", "email4@gmail.com")
                .param("name", "name4")
                .param("password", "Some valid password1!"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/profile")))
                .andReturn().getRequest().getSession();

        assertEquals(member, session.getAttribute("account"));
    }

}
