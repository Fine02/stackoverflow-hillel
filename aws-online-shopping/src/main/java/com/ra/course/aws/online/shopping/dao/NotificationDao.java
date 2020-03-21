package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;

public interface NotificationDao {
    SMSNotification createSMSNotification(SMSNotification smsNotification);

    EmailNotification createEmailNotification(EmailNotification emailNotification);

    String foundMemberEmail(String string);

    String foundMemberPhoneNumber(String string);
}
