package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface NotificationService {
    SMSNotification sendSMSNotificationAboutShipmentStatus(Shipment shipment, ShipmentLog shipmentLog, Member member);
    EmailNotification sendEmailNotificationAboutShipmentStatus(Shipment shipment,ShipmentLog shipmentLog, Member member);
    SMSNotification sendSMSNotificationAboutOrderStatus(Order order, OrderLog orderLog, Member member);
    EmailNotification sendEmailNotificationAboutOrderStatus(Order order, OrderLog orderLog, Member member);
}
