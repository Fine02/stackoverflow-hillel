package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;
import com.ra.course.aws.online.shopping.service.NotificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {
    private transient final NotificationDao notificationDao;
    private transient final OrderDao orderDao;
    private transient final ShippingDao shippingDao;


    private transient final static String ORDER_MESSAGE = "your order number";
    private transient final static String SHIPMENT_MESSAGE = "your shipment number";
    private transient final static String END_MESSAGE = "has changed status on";
    private transient final static String MESSAGE_NO_PHONE = "There is not found the phone number";
    private transient final static String MESSAGE_NO_EMAIL = "There is not found the member's email";

    public NotificationServiceImpl(final NotificationDao notificationDao, final OrderDao orderDao, final ShippingDao shippingDao) {
        this.notificationDao = notificationDao;
        this.orderDao = orderDao;
        this.shippingDao = shippingDao;
    }

    @Override
    public SMSNotification sendSMSNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) {
        final var foundShipmentLog = shippingDao.findShipmentLogById(shipmentLog.getId());
        if (shippingDao.isThisShipmentLogExist(foundShipmentLog)) {
            throw new NotificationException("SMS-notification about shipment status can not be sent");
        }

        final var foundPhoneNumber = notificationDao.foundMemberPhoneNumber(member.getAccount().getPhone());
        if (foundPhoneNumber==null) {
            throw new MemberDataNotFoundException(MESSAGE_NO_PHONE);
        }

        final String contextMessage = messageMaker(SHIPMENT_MESSAGE, shipmentLog.getShipmentNumber(), shipmentLog.getStatus().toString());
        final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
        notificationDao.createSMSNotification(smsNotification);
        return smsNotification;

    }

    @Override
    public SMSNotification sendSMSNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) {
        final var foundOrderLog = orderDao.findOrderLogById(orderLog.getId());
        if (orderDao.isThisOrderLogExist(foundOrderLog)) {
            throw new NotificationException("SMS-notification about order status can not be sent");
        }

        final var foundPhoneNumber = notificationDao.foundMemberPhoneNumber(member.getAccount().getPhone());
        if (foundPhoneNumber==null) {
            throw new MemberDataNotFoundException(MESSAGE_NO_PHONE);
        }

        final String contextMessage = messageMaker(ORDER_MESSAGE, orderLog.getOrderNumber(), orderLog.getStatus().toString());
        final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
        notificationDao.createSMSNotification(smsNotification);
        return smsNotification;
    }

    @Override
    public EmailNotification sendEmailNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) {
        final var foundShipmentLog = shippingDao.findShipmentLogById(shipmentLog.getId());
        if (shippingDao.isThisShipmentLogExist(foundShipmentLog)) {
            throw new NotificationException("Email-notification about shipment status can not be sent");
        }

        final var foundEmail = notificationDao.foundMemberEmail(member.getAccount().getEmail());
        if (foundEmail==null) {
            throw new MemberDataNotFoundException(MESSAGE_NO_EMAIL);
        }

        final String contextMessage = messageMaker(SHIPMENT_MESSAGE, shipmentLog.getShipmentNumber(), shipmentLog.getStatus().toString());
        final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
        notificationDao.createEmailNotification(emailNotification);
        return emailNotification;
    }

    @Override
    public EmailNotification sendEmailNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) {
        final var foundOrderLog = orderDao.findOrderLogById(orderLog.getId());
        if (orderDao.isThisOrderLogExist(foundOrderLog)) {
            throw new NotificationException("Email-notification about order status can not be sent");
        }

        final var foundEmail = notificationDao.foundMemberEmail(member.getAccount().getEmail());
        if (foundEmail==null) {
            throw new MemberDataNotFoundException(MESSAGE_NO_EMAIL);
        }

        final String contextMessage = messageMaker(ORDER_MESSAGE, orderLog.getOrderNumber(), orderLog.getStatus().toString());
        final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
        notificationDao.createEmailNotification(emailNotification);
        return emailNotification;
    }


    public String messageMaker(final String messageBegin, final String orderNumber, final String status) {
        return messageBegin + " " + orderNumber + " " + END_MESSAGE + " " + status;
    }
}
