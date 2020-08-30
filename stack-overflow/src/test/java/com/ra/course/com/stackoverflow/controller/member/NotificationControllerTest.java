package com.ra.course.com.stackoverflow.controller.member;

import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getNotificationDto;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
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

    @BeforeEach
    void setUp() {
        member = getSessionMemberDto();
    }

    @Test
    void whenGetAllNotificationsOfMember() throws Exception {
        //given
        var expectedList = List.of(getNotificationDto());
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
    void whenViewQuestionByNotificationId() throws Exception{
        //given
        when(service.readNotificationAndGetViewedQuestionId(1L)).thenReturn(1L);
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/members/notifications/1"))
                .andDo(print())
                .andExpect(ResultMatcher.matchAll(
                        status().isFound(),
                        redirectedUrl("/view/question/1")
                ));
    }
}
