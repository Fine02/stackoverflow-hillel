package com.ra.course.com.stackoverflow.service.notifaction;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class NotificationService {

    private transient final MemberRepository memberData;

    public boolean sendNotificationToMember(final String content, final Member member) {
        if (content.isBlank()) {
            return false;
        } else {
                final var optionalMember = memberData.findById(member.getId());
                final var memberFromDB = optionalMember.orElseThrow(()-> new MemberNotFoundException("No such member in DB"));
                final var notification = new Notification(content);
                final List<Notification> notificationsList = memberFromDB.getNotifications();
                notificationsList.add(notification);
                memberFromDB.setNotifications(notificationsList);
                memberData.update(memberFromDB);
                return true;
        }
    }

}