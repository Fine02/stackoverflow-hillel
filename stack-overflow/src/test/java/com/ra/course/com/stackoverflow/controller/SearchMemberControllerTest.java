package com.ra.course.com.stackoverflow.controller;

import com.ra.course.com.stackoverflow.dto.MemberDto;
import com.ra.course.com.stackoverflow.service.storage.MemberStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.ra.course.com.stackoverflow.controller.ControllerConstants.MEMBERS_VIEW;
import static com.ra.course.com.stackoverflow.controller.ControllerConstants.SEARCH_URL;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SearchMemberController.class)
public class SearchMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberStorageService service;

    @Test
    public void whenPostSearchMemberThenReturnListOfMembersWithView() throws Exception {
        //given
        var memberDto = mockMemberDto();
        var memberList = new ArrayList<>(List.of(memberDto, memberDto, memberDto));
        when(service.findByMemberName("MemberName")).thenReturn(memberList);
        //then
        mockMvc.perform(get(SEARCH_URL)
                .param("name", "MemberName"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        model().attributeExists("size"),
                        model().attribute("viewMembers", memberList),
                        view().name(MEMBERS_VIEW)));
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
}
