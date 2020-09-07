package com.ra.course.com.stackoverflow.controller.search;

import com.ra.course.com.stackoverflow.dto.member.MemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.entity.enums.AccountStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SearchMemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private MemberDto member;

    @BeforeEach
    void setUp() {
        member = new MemberDto();
        member.setId(1L);
        member.setName("name1");
        member.setEmail("email1@gmail.com");
        member.setRole(AccountRole.USER);
        member.setStatus(AccountStatus.ACTIVE);
    }

    @Test
    void whenSearchMemberByName() throws Exception {

        mockMvc.perform(get("/search/members")
                .param("name", "name1"))
                .andDo(print())
                .andExpect(matchAll(
                        status().isOk(),
                        view().name("search/list-members"),
                        model().attribute("members", List.of(member))))
                .andReturn().getRequest();
    }


}
