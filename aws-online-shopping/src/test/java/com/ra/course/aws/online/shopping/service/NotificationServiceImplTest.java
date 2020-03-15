package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.dao.ShippingDao;
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
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {
    private NotificationServiceImpl notificationService;
    private NotificationDao notificationDao = mock(NotificationDao.class);
    private OrderDao orderDao = mock(OrderDao.class);
    private ShippingDao shippingDao = mock(ShippingDao.class);
    private Address ADDRESS_IN_DB;

    private final OrderLog ORDER_LOG = mockOrderLog("101010", LocalDateTime.now(), OrderStatus.PENDING);
    private final ShipmentLog SHIPMENT_LOG = mockShipmentLog("101010", ShipmentStatus.SHIPPED, LocalDateTime.now());

    private String email = "hhhhh@gmail.com";
    private String phone = "456522";

    private Account account = mockAccount(email, phone);
    private Member member = mockMember(account);

    @BeforeEach
    public void before() {
        ADDRESS_IN_DB = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
        notificationService = new NotificationServiceImpl(notificationDao, orderDao, shippingDao);

        when(notificationDao.foundMemberPhoneNumber(member.getAccount().getPhone())).thenReturn(phone);
        when(orderDao.findOrderLogById(ORDER_LOG.getId())).thenReturn(ORDER_LOG);
        when(shippingDao.findShipmentLogById(SHIPMENT_LOG.getId())).thenReturn(SHIPMENT_LOG);

        when(notificationDao.foundMemberEmail(member.getAccount().getEmail())).thenReturn(email);
        when(orderDao.findOrderLogById(ORDER_LOG.getId())).thenReturn(ORDER_LOG);
        when(shippingDao.findShipmentLogById(SHIPMENT_LOG.getId())).thenReturn(SHIPMENT_LOG);
    }

    @Test
    public void whenNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException() {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(true);

        Throwable exceptionEmail = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT_LOG, member);
        });

        Throwable exceptionSMS = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT_LOG, member);
        });

        assertEquals(exceptionEmail.getMessage(), "Email-notification about shipment status can not be sent");

        assertEquals(exceptionSMS.getMessage(), "SMS-notification about shipment status can not be sent");

    }

    @Test
    public void whenNotificationAboutOrderStatusWasNotSentThanThrowNotificationException() {
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(true);

        Throwable exceptionEmail = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(ORDER_LOG, member);
        });

        Throwable exceptionSMS = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(ORDER_LOG, member);
        });

        assertEquals(exceptionSMS.getMessage(), "SMS-notification about order status can not be sent");

        assertEquals(exceptionEmail.getMessage(), "Email-notification about order status can not be sent");
    }

    @Test
    public void whenNotFoundEmailOfMemberThanEmailNotificationAboutOrderStatusWillNotBeSentThanThrowMemberNotFoundException() {
        Account account = mockAccount("", phone);
        var member = mockMember(account);

        when(notificationDao.foundMemberEmail(member.getAccount().getEmail())).thenReturn(" ");
        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(ORDER_LOG, member);
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT_LOG, member);
        });

        assertEquals(exception.getMessage(), "There is not found the member's email");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }


    @Test
    public void whenShipmentStatusWasChangedSendSMSNotification() {
        Account account = mockAccount(email, " ");
        var member = mockMember(account);

        when(notificationDao.foundMemberPhoneNumber(member.getAccount().getPhone())).thenReturn(" ");

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(ORDER_LOG, member);
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT_LOG, member);
        });

        assertEquals(exception.getMessage(), "There is not found the phone number");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }


    @Test
    public void whenShipmentStatusWasChangedSendNotification() {

        ShipmentLog newShipmentLog = mockShipmentLog("55785", ShipmentStatus.ONHOLD, LocalDateTime.now().minusDays(1));
        String expectedResult = "your shipment number 55785 has changed status on ONHOLD";

        var resultSMS = notificationService.sendSMSNotificationAboutShipmentStatus(newShipmentLog, member);
        var resultEmail = notificationService.sendEmailNotificationAboutShipmentStatus(newShipmentLog, member);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }

    @Test
    public void whenOrderStatusWasChangedSendNotification() {
        OrderLog newOrderLog = mockOrderLog("5548541", LocalDateTime.now().minusDays(1), OrderStatus.PENDING);
        String expectedResult = "your order number 5548541 has changed status on PENDING";

        var resultSMS = notificationService.sendSMSNotificationAboutOrderStatus(newOrderLog, member);
        var resultEmail = notificationService.sendEmailNotificationAboutOrderStatus(newOrderLog, member);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }


    private ShipmentLog mockShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(shipmentNumber, status, creationDate);
    }

    private OrderLog mockOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
    }

    private Account mockAccount(String email, String phoneNumber) {
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
                email,
                phoneNumber,
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
