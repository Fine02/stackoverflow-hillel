package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private SessionMemberDto member;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(1L);
            member.setName("name1");
            member.setRole(AccountRole.USER);
    }

    @Test
    void whenGetAllNotificationsOfMember() throws Exception {

        final var notification1 = new NotificationDto();
            notification1.setId(1L);
            notification1.setText("Your question was created");
            notification1.setRead(true);
        final var notification2 = new NotificationDto();
            notification2.setId(2L);
            notification2.setText("Your question was voting up");
            notification2.setRead(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/members/notifications")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("member/notifications"),
                        model().attribute("notifications", List.of(notification1, notification2))
                ));
    }

    @Test
    void whenViewQuestionByNotificationId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members/notifications/1")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
    }
}
