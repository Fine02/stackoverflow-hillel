package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;

public interface NotificationService {
    SMSNotification sendSMSNotificationAboutShipmentStatus(ShipmentLog shipmentLog, Member member) throws NotificationException, MemberNotFoundException;
    EmailNotification sendEmailNotificationAboutShipmentStatus(ShipmentLog shipmentLog, Member member) throws NotificationException, MemberNotFoundException;
    SMSNotification sendSMSNotificationAboutOrderStatus(OrderLog orderLog, Member member) throws NotificationException, MemberNotFoundException;
    EmailNotification sendEmailNotificationAboutOrderStatus(OrderLog orderLog, Member member) throws NotificationException, MemberNotFoundException;
}
