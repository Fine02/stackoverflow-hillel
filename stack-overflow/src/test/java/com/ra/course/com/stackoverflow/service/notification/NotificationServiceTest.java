package com.ra.course.com.stackoverflow.service.notification;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import com.ra.course.com.stackoverflow.service.notifaction.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    private NotificationService notificationService;
    private MemberRepository memberData = mock(MemberRepository.class);
    private long ID = 1L;
    private boolean addNotification;
    private Exception exception;

    @BeforeEach
    void  setUp(){
        notificationService = new NotificationService(memberData);
    }

    @Test
    public void whenNotificationIsAddedToListThenReturnTrue() {
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        assertEquals(0, member.getNotifications().size());
        addNotification = notificationService.sendNotificationToMember("Some notification", member);
        //then
        assertEquals(1, member.getNotifications().size());
        assertTrue(addNotification);
        verify(memberData).findById(ID);
        verify(memberData).update(any());
        verifyNoMoreInteractions(memberData);
    }

    @Test
    public void whenContentOfNotificationIsEmptyThenReturnFalse() {
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        assertEquals(0, member.getNotifications().size());
        addNotification = notificationService.sendNotificationToMember("  ", member);
        //then
        assertEquals(0, member.getNotifications().size());
        assertFalse(addNotification);
        verifyNoInteractions(memberData);
    }

    @Test
    public void whenMemberIsNotFoundThenThrowsMemberNotFoundException() {
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.empty());
        //when
        try {
            addNotification = notificationService.sendNotificationToMember("Some notification", member);
        }
        //then
        catch (Exception e){
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("No such member in DB", exception.getMessage());
        verify(memberData).findById(ID);
        verifyNoMoreInteractions(memberData);
    }

    private Member mockMember(long idMember){
        var account = Account.builder()
                .id(idMember)
                .password("password")
                .email("email")
                .name("name").build();
        return Member.builder()
                .id(idMember)
                .account(account).build();
    }
}
