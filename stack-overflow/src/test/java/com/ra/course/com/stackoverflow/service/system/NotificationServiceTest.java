package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.repository.NotificationRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getNotificationDto;
import static com.ra.course.com.stackoverflow.utils.DtoCreationUtils.getSessionMemberDto;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    private NotificationService service;

    private final NotificationRepository data = mock(NotificationRepository.class);
    private final RepositoryUtils utils = mock(RepositoryUtils.class);

    private Notification notification;
    private Answer answer;
    private Comment comment;

    @BeforeEach
    void setUp() {
        notification = getNotification();
        comment = getComment();
        answer = getAnswer();
        service = new NotificationServiceImpl(data, utils);

        when(utils.getNotificationFromDB(ID)).thenReturn(notification);
        when(utils.getAnswerFromDB(ID)).thenReturn(answer);
        when(utils.getCommentFromDB(ID)).thenReturn(comment);

    }

    @Test
    void whenSendQuestionNotification() {
        //given
        var question = getQuestion();
        notification.setText("Your question was " + TEXT);
        notification.setQuestion(ID);
        //when
        service.sendNotification(question, TEXT);
        //then
        verify(data).save(notification);
    }

    @Test
    void whenSendAnswerNotification() {
        //given
        notification.setText("Your answer was " + TEXT);
        notification.setAnswer(ID);
        //when
        service.sendNotification(answer, TEXT);
        //then
        verify(data).save(notification);
    }

    @Test
    void whenSendCommentNotification() {
        //given
        notification.setText("Your comment was " + TEXT);
        notification.setComment(ID);
        //when
        service.sendNotification(comment, TEXT);
        //then
        verify(data).save(notification);
    }

    @Test
    void whenReadNotificationWithQuestionId() {
        //given
        notification.setQuestion(ID);
        //when
        var result = service.readNotificationAndGetViewedQuestionId(ID);
        notification.setRead(true);
        //then
        assertEquals(ID, result);
        verify(data).updateRead(notification);
    }

    @Test
    void whenReadNotificationWithAnswerId() {
        //given
        notification.setAnswer(ID);
        //when
        var result = service.readNotificationAndGetViewedQuestionId(ID);
        notification.setRead(true);
        //then
        assertEquals(ID, result);
        verify(data).updateRead(notification);
    }
    @Test
    void whenReadNotificationWithCommentToQuestionId() {
        //given
        notification.setComment(ID);
        comment.setQuestion(ID);
        //when
        var result = service.readNotificationAndGetViewedQuestionId(ID);
        notification.setRead(true);
        //then
        assertEquals(ID, result);
        verify(data).updateRead(notification);
    }
    @Test
    void whenReadNotificationWithCommentToAnswerId() {
        //given
        notification.setComment(ID);
        comment.setAnswer(ID);
        //when
        var result = service.readNotificationAndGetViewedQuestionId(ID);
        notification.setRead(true);
        //then
        assertEquals(ID, result);
        verify(data).updateRead(notification);
    }

    @Test
    void whenGetAllUserNotificationThenReturnListWithNotification() {
        //given
        var sessionMember = getSessionMemberDto();
        when(data.getAllByMemberId(ID)).thenReturn(getNotificationList());
        //when
        var result = service.getAllNotificationsByMember(sessionMember);
        //then
        assertEquals(expectedList(), result);
    }

    private List<Notification> getNotificationList(){
        var note2 = getNotification();
        note2.setId(2L);
        var note3 = getNotification();
        note3.setId(3L);
        return List.of(notification, note2, note3);
    }

    private List<NotificationDto> expectedList(){
        var noteDto1 = getNotificationDto();
        var noteDto2 = getNotificationDto();
        noteDto2.setId(2L);
        var noteDto3 = getNotificationDto();
        noteDto3.setId(3L);
        return List.of(noteDto1, noteDto2, noteDto3);
    }
}
