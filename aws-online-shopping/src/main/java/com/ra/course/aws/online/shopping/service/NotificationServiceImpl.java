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
    private final String ORDER_MESSAGE ="your order number ";
    private final String SHIPMENT_MESSAGE ="your shipment number ";
    private final String END_MESSAGE =" has changed status on ";

    public NotificationServiceImpl(final NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public SMSNotification sendSMSNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) throws NotificationException, MemberNotFoundException {
        final var foundShipmentLog =notificationDao.findShipmentLogById(shipmentLog.getId());
        final var foundShipment = notificationDao.findByShipmentNumber(shipmentLog.getShipmentNumber());
        final var foundShipmentList = notificationDao.findLogListByShipment(foundShipment.getShipmentLogs());
        if (notificationDao.isThisShipmentLogExist(foundShipmentLog)) {
            throw new NotificationException("SMS-notification about shipment status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            notificationDao.addShipmentLog(foundShipmentList.add(shipmentLog));
            notificationDao.updateShipment(foundShipment);
            final String contextMessage= messageMaker(SHIPMENT_MESSAGE, shipmentLog.getShipmentNumber(), shipmentLog.getStatus().toString());
            final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
            notificationDao.createSMSNotification(smsNotification);
            return smsNotification;
        }
        throw new MemberNotFoundException("There is not found the phone number");
    }

    @Override
    public EmailNotification sendEmailNotificationAboutShipmentStatus(final ShipmentLog shipmentLog, final Member member) throws NotificationException, MemberNotFoundException {
        final var foundShipmentLog =notificationDao.findShipmentLogById(shipmentLog.getId());
        final var foundShipment = notificationDao.findByShipmentNumber(shipmentLog.getShipmentNumber());
        final var foundShipmentList = notificationDao.findLogListByShipment(foundShipment.getShipmentLogs());
        if (notificationDao.isThisShipmentLogExist(foundShipmentLog)) {
            throw new NotificationException("Email-notification about shipment status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            notificationDao.addShipmentLog(foundShipmentList.add(shipmentLog));
            notificationDao.updateShipment(foundShipment);
            final String contextMessage= messageMaker(SHIPMENT_MESSAGE, shipmentLog.getShipmentNumber(), shipmentLog.getStatus().toString());
            final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
            notificationDao.createEmailNotification(emailNotification);
            return emailNotification;
        }
        throw new MemberNotFoundException("There is not found the email");
    }


    @Override
    public SMSNotification sendSMSNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) throws NotificationException, MemberNotFoundException {
        final var foundOrderLog = notificationDao.findOrderLogById(orderLog.getId());
        final var foundOrder = notificationDao.findByOrderNumber(orderLog.getOrderNumber());
        final var foundOrderList = notificationDao.findLogListByOrder(foundOrder.getOrderLog());
        if (notificationDao.isThisOrderLogExist(foundOrderLog)) {
            throw new NotificationException("SMS-notification about order status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            notificationDao.addOrderLog(foundOrderList.add(orderLog));
            foundOrder.setStatus(orderLog.getStatus());
            notificationDao.updateOrder(foundOrder);
            final String contextMessage= messageMaker(ORDER_MESSAGE, orderLog.getOrderNumber(), orderLog.getStatus().toString());
            final var smsNotification = new SMSNotification(LocalDateTime.now(), contextMessage, member.getAccount().getPhone());
            notificationDao.createSMSNotification(smsNotification);
            return smsNotification;
        }
        throw new MemberNotFoundException("There is not found the member's phone number");
    }

    @Override
    public EmailNotification sendEmailNotificationAboutOrderStatus(final OrderLog orderLog, final Member member) throws NotificationException, MemberNotFoundException {
        final var foundOrderLog = notificationDao.findOrderLogById(orderLog.getId());
        final var foundOrder = notificationDao.findByOrderNumber(orderLog.getOrderNumber());
        final var foundOrderList = notificationDao.findLogListByOrder(foundOrder.getOrderLog());
        if (notificationDao.isThisOrderLogExist(foundOrderLog)) {
            throw new NotificationException("Email-notification about order status can not be sent");
        }
        if (notificationDao.isFoundMemberEmail(member.getAccount().getEmail())) {
            notificationDao.addOrderLog(foundOrderList.add(orderLog));
            foundOrder.setStatus(orderLog.getStatus());
            notificationDao.updateOrder(foundOrder);
            final String contextMessage= messageMaker(ORDER_MESSAGE, orderLog.getOrderNumber(), orderLog.getStatus().toString());
            final var emailNotification = new EmailNotification(LocalDateTime.now(), contextMessage, member.getAccount().getEmail());
            notificationDao.createEmailNotification(emailNotification);
            return emailNotification;
        }
        throw new MemberNotFoundException("There is not found the member's email");
    }


    public String messageMaker(String messageBegin, String orderNumber, String status){
        return messageBegin+orderNumber+END_MESSAGE+status;
    }
}
