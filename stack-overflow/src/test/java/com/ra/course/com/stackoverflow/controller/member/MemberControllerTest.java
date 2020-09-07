package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.dto.member.UpdateDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.*;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    private MemberService service;

    private MemberDto memberDto;
    private UpdateDto updateDto;
    private SessionMemberDto member;

    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(ID);
            member.setName("Member name");
            member.setRole(AccountRole.USER);

        memberDto = new MemberDto();
            memberDto.setId(ID);
            memberDto.setName("Member Name");
            memberDto.setEmail("email@gmail.com");
            memberDto.setRole(AccountRole.USER);
            memberDto.setStatus(AccountStatus.ACTIVE);

        updateDto = new UpdateDto();
            updateDto.setName("New name");
            updateDto.setPassword("New password!1");
    }

    @Test
    public void whenViewProfile() throws Exception {
        //given
        when(service.getMemberById(ID)).thenReturn(memberDto);
        //then
        mockMvc.perform(get("/members/profile")
                    .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attribute("profile", memberDto),
                        view().name("member/profile")));

    }

    @Test
    public void whenGetUpdate() throws Exception {
        mockMvc.perform(get("/members/update")
                    .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("dto"),
                        view().name("member/update")));

    }

    @Test
    public void whenPostUpdateThenSessionAttrChanged() throws Exception {
        //then
        mockMvc.perform(post("/members/update")
                    .params(new LinkedMultiValueMap<>() {{
                        add("name", "New name");
                        add("password", "New password!1");
                        add("currentPassword", "current password");
                    }})
                    .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/logout")
                ));
        verify(service).updateMember(updateDto, member, "current password");
    }

    @Test
    public void whenPostDeleteThenOk() throws Exception {
        //then
        mockMvc.perform(post("/members/delete")
                .param("currentPassword", "password")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(matchAll(
                        status().isFound(),
                        redirectedUrl("/members/logout")));
        verify(service).deleteMember(member, "password");
    }

    @Test
    public void whenLogOutThenReturnMainTemplate() throws Exception {
        //then
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
