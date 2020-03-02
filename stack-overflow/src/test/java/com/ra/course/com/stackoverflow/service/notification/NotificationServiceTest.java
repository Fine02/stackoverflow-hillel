package com.ra.course.com.stackoverflow.service.notification;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
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

    @BeforeEach
    void  setUp(){
        notificationService = new NotificationService(memberData);
    }

    @Test
    public void whenNotificationIsAddedToListThenReturnTrue() throws DataBaseOperationException{
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        assertEquals(0, member.getNotifications().size());
        var addNotification = notificationService.sendNotificationToMember("Some notification", member);
        //then
        assertEquals(1, member.getNotifications().size());
        assertTrue(addNotification);
    }

    @Test
    public void whenContentOfNotificationIsEmptyThenReturnFalse() throws DataBaseOperationException{
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.of(member));
        //when
        assertEquals(0, member.getNotifications().size());
        var addNotification = notificationService.sendNotificationToMember("  ", member);
        //then
        assertEquals(0, member.getNotifications().size());
        assertFalse(addNotification);
    }

    @Test
    public void whenMemberIsNotFoundThenReturnFalse() throws DataBaseOperationException {
        //given
        var member = mockMember(ID);
        when(memberData.findById(ID)).thenReturn(Optional.empty());
        //when
        var addNotification = notificationService.sendNotificationToMember("Some notification", member);
        //then
        assertFalse(addNotification);
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
