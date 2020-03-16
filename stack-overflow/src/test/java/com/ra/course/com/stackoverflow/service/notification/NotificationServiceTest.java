package com.ra.course.com.stackoverflow.service.notification;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import com.ra.course.com.stackoverflow.service.notifaction.NotificationService;
import com.ra.course.com.stackoverflow.service.notifaction.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {
    private final MemberRepository memberData = mock(MemberRepository.class);
    private final long ID = 1;

    private NotificationService notificationService;
    private boolean addNotification;
    private Member member;

    @BeforeEach
    void  setUp(){
        notificationService = new NotificationServiceImpl(memberData);
        member = mockMember();
    }

    @Test
    public void whenNotificationIsAddedToListThenReturnTrue() {
        //given
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        addNotification = notificationService.sendNotificationToMember("Some notification", member);
        //then
        assertTrue(addNotification);
    }

    @Test
    public void whenContentOfNotificationIsEmptyThenReturnFalse() {
        //given
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        addNotification = notificationService.sendNotificationToMember("  ", member);
        //then
        assertFalse(addNotification);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        when(memberData.findById(ID)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> notificationService.sendNotificationToMember("Some notification", member))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("No such member in DB");
    }

    private Member mockMember(){
        var account = Account.builder()
                .id(ID)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .id(ID)
                .account(account).build();
    }
}
