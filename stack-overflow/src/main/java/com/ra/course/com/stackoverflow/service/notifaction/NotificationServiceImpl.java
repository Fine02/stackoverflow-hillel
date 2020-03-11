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
        final var optionalMember = memberData.findById(member.getId());
        final var memberFromDB = optionalMember.orElseThrow(() -> new MemberNotFoundException("No such member in DB"));
        final var notification = new Notification(content);
        memberFromDB.getNotifications().add(notification);
        memberData.update(memberFromDB);
        return true;
    }

}