package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

public interface SmsNotificationService {
    SMSNotification sendSMSNotificationAboutOrderStatus(Order order, OrderLog orderLog, Member member);
    SMSNotification sendSMSNotificationAboutShipmentStatus(Shipment shipment, ShipmentLog shipmentLog, Member member);
}
