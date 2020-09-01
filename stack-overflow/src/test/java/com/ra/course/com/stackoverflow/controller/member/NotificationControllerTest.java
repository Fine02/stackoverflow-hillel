package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.enums.AccountRole;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    private SessionMemberDto member;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        member = new SessionMemberDto();
            member.setId(ID);
            member.setName("Member name");
            member.setRole(AccountRole.USER);
    }

    @Test
    void whenGetAllNotificationsOfMember() throws Exception {
        //given
        var expectedList = List.of(createNotification());
        when(service.getAllNotificationsByMember(member)).thenReturn(expectedList);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/members/notifications")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isOk(),
                        view().name("member/notifications"),
                        model().attribute("notifications", expectedList)
                ));
    }

    @Test
    void whenViewQuestionByNotificationId() throws Exception {
        //given
        when(service.readNotificationAndGetViewedQuestionId(ID)).thenReturn(ID);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/members/notifications/1")
                .sessionAttr("account", member))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
    }

    private NotificationDto createNotification(){
        final var notification = new NotificationDto();
            notification.setId(ID);
            notification.setCreationTime(LocalDateTime.MIN);
        return notification;
    }
}
