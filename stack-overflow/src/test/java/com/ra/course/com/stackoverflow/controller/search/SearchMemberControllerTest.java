package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.service.search.SearchMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getMemberDto;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchMemberController.class)
public class SearchMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchMemberService service;

    @Test
    void whenSearchMemberByName() throws Exception {
        //given
        var list = List.of(getMemberDto());
        when(service.findByMemberName("Member name")).thenReturn(list);
        //then
        mockMvc.perform(get("/search/members")
                .param("name", "Member name"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-members"),
                        model().attribute("members", list)));
    }
}
