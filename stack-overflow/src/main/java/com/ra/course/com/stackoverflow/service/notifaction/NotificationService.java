package com.ra.course.com.stackoverflow.service.notifaction;

import com.ra.course.com.stackoverflow.entity.Member;

public interface NotificationService {

    boolean sendNotificationToMember(final String content, final Member member);

}
