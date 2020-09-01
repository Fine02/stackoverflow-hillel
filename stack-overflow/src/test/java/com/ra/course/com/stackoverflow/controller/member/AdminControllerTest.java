package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.member.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService service;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("Member name");
            member.setRole(AccountRole.ADMIN);
    }

    @Test
    void whenBlockUser() throws Exception{
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/1/block")
                        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/member/1")
                ));
        verify(service).blockMember(1L, member);
    }
    @Test
    void whenUnblockUser() throws Exception{
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/1/unblock")
                        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/member/1")
                ));
        verify(service).unblockMember(1L, member);
    }
    @Test
    void whenSetUserAsModer() throws Exception{
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/1/moder")
                        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/member/1")
                ));
        verify(service).setMemberAsModer(1L, member);
    }
    @Test
    void whenSetUserAsAdmin() throws Exception{
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/1/admin")
                        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/member/1")
                ));
        verify(service).setMemberAsAdmin(1L, member);
    }
    @Test
    void whenSetAsUser() throws Exception{
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/1/user")
                        .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/member/1")
                ));
        verify(service).setMemberAsUser(1L, member);
    }
}
