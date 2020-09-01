package com.ra.course.com.stackoverflow.controller.view;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViewMemberController.class)
public class ViewMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void whenViewMemberByIdThenReturnMemberDto() throws Exception {
        //given
        final var member = getMember();
        when(memberService.getMemberById(1L)).thenReturn(member);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/view/member/1"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("view/member"),
                        model().attribute("member", member)
                ));
    }

    private MemberDto getMember(){
        final var member = new MemberDto();
            member.setId(1L);
            member.setName("Name");
            member.setEmail("email@email.com");
        return member;
    }
}
