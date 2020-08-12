package com.ra.course.com.stackoverflow.service.system;

import com.ra.course.com.stackoverflow.dto.NotificationDto;
import com.ra.course.com.stackoverflow.dto.member.SessionMemberDto;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Comment;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;

import java.util.List;

public interface NotificationService {

    void sendNotification(Question question, String event);

    void sendNotification(Answer answer, String event);

    void sendNotification(Comment comment, String event);

    List<NotificationDto> getAllNotificationsByMember(SessionMemberDto sessionMember);

    Long readNotificationAndGetViewedQuestionId(Long notificationId);

}
