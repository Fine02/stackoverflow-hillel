package com.ra.course.com.stackoverflow.service.notifaction;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.MemberRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private transient final MemberRepository memberData;

    @Override
    public boolean sendNotificationToMember(final String content, final Member member) {
        if (content.isBlank()) {
            return false;
        }
        final var memberFromDB = memberData.findById(member.getId()).
                orElseThrow(() -> new MemberNotFoundException("No such member in DB"));
        memberFromDB.getNotifications().add(new Notification(content));
        memberData.update(memberFromDB);
        return true;
    }

}