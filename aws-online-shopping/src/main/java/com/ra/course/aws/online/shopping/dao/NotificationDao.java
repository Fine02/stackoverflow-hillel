package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface NotificationDao {


    SMSNotification createSMSNotification(SMSNotification smsNotification);

    EmailNotification createEmailNotification(EmailNotification emailNotification);

    boolean isFoundMemberEmail(String string);
    boolean isFoundMemberData(boolean string);
    String foundMemberEmail (String string);
    boolean isFoundMemberDataEmail (boolean string);
    Member findMemberById(Long id);
    boolean isMemberFound(Member member);

}
