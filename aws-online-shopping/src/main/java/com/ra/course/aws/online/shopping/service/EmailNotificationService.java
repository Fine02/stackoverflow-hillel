package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface EmailNotificationService {
    EmailNotification sendEmailNotificationAboutOrderStatus(OrderLog orderLog, Member member);
    EmailNotification sendEmailNotificationAboutShipmentStatus(ShipmentLog shipmentLog, Member member);
}
