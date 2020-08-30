package com.ra.course.com.stackoverflow.service.system.impl;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.dto.mapper.NotificationMapper;
import com.ra.course.com.stackoverflow.repository.NotificationRepository;
import com.ra.course.com.stackoverflow.service.RepositoryUtils;
import com.ra.course.com.stackoverflow.service.system.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationData;
    private final RepositoryUtils utils;

    @Override
    public void sendNotification(final Question question, final String event) {
        final var notification = createNotification(question.getAuthor(),"Your question was " + event );
        notification.setQuestion(question.getId());
        notificationData.save(notification);
    }

    @Override
    public void sendNotification(final Answer answer, final String event) {
        final var notification = createNotification(answer.getAuthor(),"Your answer was " + event);
        notification.setAnswer(answer.getId());
        notificationData.save(notification);
    }

    @Override
    public void sendNotification(final Comment comment, final String event) {
        final var notification = createNotification(comment.getAuthor(),"Your comment was " + event);
        notification.setComment(comment.getId());
        notificationData.save(notification);
    }

    @Override
    public Long readNotificationAndGetViewedQuestionId(final Long notificationId) {
        final var note = utils.getNotificationFromDB(notificationId);
            note.setRead(true);
        notificationData.updateRead(note);

        if(Objects.nonNull(note.getQuestion())){
            return note.getQuestion();
        }
        if(Objects.nonNull(note.getAnswer())){
            return utils.getAnswerFromDB(note.getAnswer()).getId();
        }
        final var comment = utils.getCommentFromDB(note.getComment());
        if(Objects.nonNull(comment.getQuestion())){
            return comment.getQuestion();
        }
        return utils.getAnswerFromDB(comment.getAnswer()).getQuestion();
    }

    @Override
    public List<NotificationDto> getAllNotificationsByMember(final SessionMemberDto sessionMember) {
        final var notifications = notificationData.getAllByMemberId(sessionMember.getId());
        return NotificationMapper.MAPPER.toNotificationDto(notifications);
    }

    private Notification createNotification(final Long author, final String text){
        final var notification = new Notification();
            notification.setMember(author);
            notification.setText(text);
        return notification;
    }
}