package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationServiceImplTest {
    private NotificationServiceImpl notificationService;
    private NotificationDao notificationDao = mock(NotificationDao.class);
    private Exception exception;
    private final Address ADDRESS_IN_DB = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
    private final String PHONE_NUMBER = "10101010";
    LocalDateTime constantLocalDateTime = LocalDateTime.of(2020, Month.FEBRUARY, 20, 06, 30);
    private final ShipmentLog SHIPMENT_LOG_IN_DB = mockShipmentLog("777", ShipmentStatus.SHIPPED, constantLocalDateTime);
    private final Member MEMBER_IN_DB = mockMember(mockAccount());

    @BeforeEach
    public void before() {
        notificationService = new NotificationServiceImpl(notificationDao);
    }

    @Test
    public void whenSMSNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException() throws MemberNotFoundException {
        when(notificationDao.isThisShipmentLogExist(SHIPMENT_LOG_IN_DB)).thenReturn(true);
        try {
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT_LOG_IN_DB, mockMember(mockAccount()));
        } catch (NotificationException e) {
            exception = e;
        }
        assertTrue(exception instanceof NotificationException);
        assertEquals("SMS-notification about shipment status can not be sent", exception.getMessage());
    }

    @Test
    public void whenEmailNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException() throws MemberNotFoundException {
        when(notificationDao.isThisShipmentLogExist(SHIPMENT_LOG_IN_DB)).thenReturn(true);
        try {
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT_LOG_IN_DB, mockMember(mockAccount()));
        } catch (NotificationException e) {
            exception = e;
        }
        assertTrue(exception instanceof NotificationException);
        assertEquals("Email-notification about shipment status can not be sent", exception.getMessage());
    }

    @Test
    public void whenEmailNotificationAboutOrderStatusWasNotSentThanThrowNotificationException() throws MemberNotFoundException {
        var orderLog = mockOrderLog("888", constantLocalDateTime, OrderStatus.PENDING);
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(true);
        try {
            notificationService.sendEmailNotificationAboutOrderStatus(orderLog, mockMember(mockAccount()));
        } catch (NotificationException e) {
            exception = e;
        }
        assertTrue(exception instanceof NotificationException);
        assertEquals("Email-notification about order status can not be sent", exception.getMessage());
    }

    @Test
    public void whenSMSNotificationAboutOrderStatusWasNotSentThanThrowNotificationException() throws MemberNotFoundException {
        var orderLog = mockOrderLog("888", constantLocalDateTime, OrderStatus.PENDING);
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(true);
        try {
            notificationService.sendSMSNotificationAboutOrderStatus(orderLog, mockMember(mockAccount()));
        } catch (NotificationException e) {
            exception = e;
        }
        assertTrue(exception instanceof NotificationException);
        assertEquals("SMS-notification about order status can not be sent", exception.getMessage());
    }

    @Test
    public void whenNotFoundPhoneOfMemberThanSMSNotificationAboutShipmentStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        when(notificationDao.isThisShipmentLogExist(SHIPMENT_LOG_IN_DB)).thenReturn(false);
        when(notificationDao.isFoundMemberPhoneNumber(MEMBER_IN_DB.getAccount().getPhone())).thenReturn(false);
        try {
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT_LOG_IN_DB, mockMember(mockAccount()));
        } catch (MemberNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the phone number", exception.getMessage());
    }

    @Test
    public void whenNotFoundEmailOfMemberThanEmailNotificationAboutShipmentStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        when(notificationDao.isThisShipmentLogExist(SHIPMENT_LOG_IN_DB)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER_IN_DB.getAccount().getEmail())).thenReturn(false);
        try {
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT_LOG_IN_DB, mockMember(mockAccount()));
        } catch (MemberNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the email", exception.getMessage());
    }

    @Test
    public void whenNotFoundEmailOfMemberThanEmailNotificationAboutOrderStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        var orderLog = mockOrderLog("888", constantLocalDateTime, OrderStatus.PENDING);
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER_IN_DB.getAccount().getEmail())).thenReturn(false);
        try {
            notificationService.sendEmailNotificationAboutOrderStatus(orderLog, mockMember(mockAccount()));
        } catch (MemberNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the member's email", exception.getMessage());
    }

    @Test
    public void whenNotFoundPhoneNumberOfMemberThanSMSNotificationAboutOrderStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        var orderLog = mockOrderLog("888", constantLocalDateTime, OrderStatus.PENDING);
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(false);
        when(notificationDao.isFoundMemberPhoneNumber(MEMBER_IN_DB.getAccount().getPhone())).thenReturn(false);
        try {
            notificationService.sendSMSNotificationAboutOrderStatus(orderLog, mockMember(mockAccount()));
        } catch (MemberNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the member's phone number", exception.getMessage());
    }

    @Test
    public void whenShipmentStatusWasChangedSendSMSNotification() throws NotificationException, MemberNotFoundException {
        String expectedContextFromMessage = "your shipment 777 has SHIPPED status";
        var shipmentLog = mockShipmentLog("777", ShipmentStatus.SHIPPED, LocalDateTime.now());
        var member = mockMember(mockAccount());
        when(notificationDao.isThisShipmentLogExist(shipmentLog)).thenReturn(false);
        when(notificationDao.isFoundMemberPhoneNumber(MEMBER_IN_DB.getAccount().getPhone())).thenReturn(true);
        var resultOrder = notificationService.sendSMSNotificationAboutShipmentStatus(shipmentLog, member);
        assertEquals(expectedContextFromMessage, resultOrder.getContent());
    }

    @Test
    public void whenShipmentStatusWasChangedSendEmailNotification() throws NotificationException, MemberNotFoundException {
        String expectedContextFromMessage = "your shipment number 777 changed status on SHIPPED";
        var shipmentLog = mockShipmentLog("777", ShipmentStatus.SHIPPED, LocalDateTime.now());
        var member = mockMember(mockAccount());
        when(notificationDao.isThisShipmentLogExist(shipmentLog)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER_IN_DB.getAccount().getEmail())).thenReturn(true);
        var resultOrder = notificationService.sendEmailNotificationAboutShipmentStatus(shipmentLog, member);
        assertEquals(expectedContextFromMessage, resultOrder.getContent());
    }

    @Test
    public void whenOrderStatusWasChangedSendEmailNotification() throws NotificationException, MemberNotFoundException {
        String expectedContextFromMessage = "your order number 888 has changed status on PENDING";
        var orderLog = mockOrderLog("888", LocalDateTime.now(), OrderStatus.PENDING);
        var member = mockMember(mockAccount());
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER_IN_DB.getAccount().getEmail())).thenReturn(true);
        var resultOrder = notificationService.sendEmailNotificationAboutOrderStatus(orderLog, member);
        assertEquals(expectedContextFromMessage, resultOrder.getContent());
    }

    @Test
    public void whenOrderStatusWasChangedSendSMSNotification() throws NotificationException, MemberNotFoundException {
        String expectedContextFromMessage = "your order 999 has status COMPLETE";
        var orderLog = mockOrderLog("999", LocalDateTime.now(), OrderStatus.COMPLETE);
        var member = mockMember(mockAccount());
        when(notificationDao.isThisOrderLogExist(orderLog)).thenReturn(false);
        when(notificationDao.isFoundMemberPhoneNumber(MEMBER_IN_DB.getAccount().getPhone())).thenReturn(true);
        var resultOrder = notificationService.sendSMSNotificationAboutOrderStatus(orderLog, member);
        assertEquals(expectedContextFromMessage, resultOrder.getContent());
    }

    private ShipmentLog mockShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(shipmentNumber, status, creationDate);
    }

    private OrderLog mockOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
    }

    private Account mockAccount() {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, ADDRESS_IN_DB));
        List<ElectronicBankTransfer> electronicBankTransfers = new ArrayList<>();
        electronicBankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                ADDRESS_IN_DB,
                "sbwbw@sbsb.com",
                PHONE_NUMBER,
                creditCardList,
                electronicBankTransfers
        );
        return account;
    }

    private Member mockMember(Account account) {
        Member member = new Member(account);
        return member;
    }

}
