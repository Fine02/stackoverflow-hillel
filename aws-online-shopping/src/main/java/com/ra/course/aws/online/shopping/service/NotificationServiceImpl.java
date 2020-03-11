package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;

import java.time.LocalDateTime;

public class NotificationServiceImpl implements NotificationService {
    private transient final NotificationDao notificationDao;

    public NotificationServiceImpl(final NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public SMSNotification sendSMSNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) throws NotificationException, MemberNotFoundException {
        if (notificationDao.isThisShipmentLogExist(shipmentLog)) {
            throw new NotificationException("SMS-notification about shipment status can not be sent");
        }
        if (notificationDao.isFoundMemberPhoneNumber(member.getAccount().getPhone())) {
            final var foundShipment = notificationDao.findByShipmentNumber(shipmentLog.getShipmentNumber());
            final var foundShipmentList = notificationDao.findLogListByShipment(foundShipment);
            notificationDao.addShipmentLog(foundShipmentList.add(shipmentLog));
            final String contextMessage = "your shipment " + shipmentLog.getShipmentNumber() + " has " + shipmentLog.getStatus() + " status";
            final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
            notificationDao.createSMSNotification(smsNotification);
            return smsNotification;
        }
        throw new MemberNotFoundException("There is not found the phone number");
    }

    @Override
    public EmailNotification sendEmailNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) throws NotificationException, MemberNotFoundException {
        if (notificationDao.isThisShipmentLogExist(shipmentLog)) {
            throw new NotificationException("Email-notification about shipment status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            final var foundShipment = notificationDao.findByShipmentNumber(shipmentLog.getShipmentNumber());
            final var foundShipmentList = notificationDao.findLogListByShipment(foundShipment);
            notificationDao.addShipmentLog(foundShipmentList.add(shipmentLog));
            final String contextMessage = "your shipment number " + shipmentLog.getShipmentNumber() + " changed status on " + shipmentLog.getStatus();
            final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
            notificationDao.createEmailNotification(emailNotification);
            return emailNotification;
        }
        throw new MemberNotFoundException("There is not found the email");
    }


    @Override
    public SMSNotification sendSMSNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) throws NotificationException, MemberNotFoundException {
        if (notificationDao.isThisOrderLogExist(orderLog)) {
            throw new NotificationException("SMS-notification about order status can not be sent");
        }
        if (notificationDao.isFoundMemberPhoneNumber(member.getAccount().getPhone())) {
            final var foundOrder = notificationDao.findByOrderNumber(orderLog.getOrderNumber());
            final var foundOrderList = notificationDao.findLogListByOrder(foundOrder);
            notificationDao.addOrderLog(foundOrderList.add(orderLog));
            final String contextMessage = "your order " + orderLog.getOrderNumber() + " has status " + orderLog.getStatus();
            final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
            notificationDao.createSMSNotification(smsNotification);
            return smsNotification;
        }
        throw new MemberNotFoundException("There is not found the member's phone number");
    }

    @Override
    public EmailNotification sendEmailNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) throws NotificationException, MemberNotFoundException {
        if (notificationDao.isThisOrderLogExist(orderLog)) {
            throw new NotificationException("Email-notification about order status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            final var foundOrder = notificationDao.findByOrderNumber(orderLog.getOrderNumber());
            final var foundOrderList = notificationDao.findLogListByOrder(foundOrder);
            notificationDao.addOrderLog(foundOrderList.add(orderLog));
            final String contextMessage = "your order number " + orderLog.getOrderNumber() + " has changed status on " + orderLog.getStatus();
            final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
            notificationDao.createEmailNotification(emailNotification);
            return emailNotification;
        }
        throw new MemberNotFoundException("There is not found the member's email");
    }
}
