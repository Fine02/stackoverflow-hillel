package com.ra.course.com.stackoverflow.service.notifaction;

import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.exception.repository.DataBaseOperationException;
import com.ra.course.com.stackoverflow.exception.service.MemberNotFoundException;
import com.ra.course.com.stackoverflow.repository.interfaces.MemberRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class NotificationService {

    private transient final MemberRepository memberData;

    public boolean sendNotificationToMember(final String content, final Member member) throws DataBaseOperationException {
        if (content.isBlank()) {
            return false;
        } else {
            try {
                final var optionalMember = memberData.findById(member.getId());
                final Member memberFromDB = checkMemberFromDB(optionalMember);
                final var notification = new Notification(content);
                final List<Notification> notificationsList = memberFromDB.getNotifications();
                notificationsList.add(notification);
                memberFromDB.setNotifications(notificationsList);
                memberData.update(memberFromDB);
                return true;
            } catch (MemberNotFoundException e) {
                return false;
            }
        }
    }

    private Member checkMemberFromDB(final Optional<Member> optionalMember) throws MemberNotFoundException {
        if (optionalMember.isEmpty()) {
            throw new MemberNotFoundException("No such question in DB");
        } else {
            return optionalMember.get();
        }
    }

}