package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private MemberDto memberDto;
    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);

        memberDto = new MemberDto();
            memberDto.setId(1L);
            memberDto.setName("name1");
            memberDto.setEmail("email1@gmail.com");
            memberDto.setRole(AccountRole.USER);
            memberDto.setStatus(AccountStatus.ACTIVE);

    }

    @Test
    @Rollback
    @Transactional
    public void whenDeleteAccount() throws Exception {

        //delete account
        mockMvc.perform(post("/members/delete")
                .param("currentPassword", "password1")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/logout")));

        //check deleting
        memberDto.setStatus(AccountStatus.ARCHIVED);

        var member = (MemberDto) mockMvc.perform(get("/view/member/1"))
                .andReturn().getRequest().getAttribute("member");

        assertEquals(member, memberDto);
    }

    @Test
    public void whenViewProfile() throws Exception {

        mockMvc.perform(get("/members/profile")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attribute("profile", memberDto),
                        view().name("member/profile")));

    }

    @Test
    @Rollback
    @Transactional
    public void whenUpdateAccount() throws Exception {

        //update account
        mockMvc.perform(post("/members/update")
                .param("name", "New name")
                .param("password", "New password!1")
                .param("currentPassword", "password1")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/logout")
                ));

        //check updating
        memberDto.setName("New name");

        var member = (MemberDto) mockMvc.perform(get("/view/member/1"))
                .andReturn().getRequest().getAttribute("member");

        assertEquals(member, memberDto);
    }

    @Test
    public void whenLogOutThenReturnMainTemplate() throws Exception {

        var request = mockMvc.perform(get("/members/logout")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/")))
                .andReturn().getRequest();
        assertNull(request.getSession().getAttribute("account"));
    }
}
