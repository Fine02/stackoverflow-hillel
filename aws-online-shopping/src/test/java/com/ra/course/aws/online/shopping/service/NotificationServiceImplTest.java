package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.NotificationDao;
import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.AccountStatus;
import com.ra.course.aws.online.shopping.entity.user.Member;

import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
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
    private  Member MEMBER = mockMember(mockAccount());

    private  ShipmentLog SHIPMENT_LOG = mockShipmentLog();
    private  List <ShipmentLog> SHIPMENT_LOG_LIST = mockListOfShipmentLog(SHIPMENT_LOG);
    private  Shipment SHIPMENT =mockShipment();


    private  OrderLog ORDER_LOG = mockOrderLog();
    private  List<OrderLog> ORDER_LOG_LIST = mockOrderLogList(ORDER_LOG);
    private  Order ORDER = mockOrder();

    private  String EXPECTED_MESSAGE_ABOUT_ORDER_STATUS ="your order number 888 has changed status on PENDING";
    private  String EXPECTED_MESSAGE_ABOUT_SHIPMENT_STATUS ="your shipment number 777 has changed status on SHIPPED";

    @BeforeEach
    public void before() {
        ADDRESS_IN_DB=new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");
        notificationService = new NotificationServiceImpl(notificationDao, orderDao, shippingDao);

        when(orderDao.findOrderLogById(ORDER_LOG.getId())).thenReturn(ORDER_LOG);
        when(orderDao.findByOrderNumber(ORDER_LOG.getOrderNumber())).thenReturn(ORDER);
        when(orderDao.findLogListByOrder(ORDER.getOrderLog())).thenReturn(ORDER_LOG_LIST);

        when(shippingDao.findShipmentLogById(SHIPMENT_LOG.getId())).thenReturn(SHIPMENT_LOG);
        when(shippingDao.findByShipmentNumber(SHIPMENT_LOG.getShipmentNumber())).thenReturn(SHIPMENT);
        when(shippingDao.findLogListByShipment(SHIPMENT.getShipmentLogs())).thenReturn(SHIPMENT_LOG_LIST);
    }

    @Test
    public void whenSMSNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException()  {

        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(true);

        Throwable exception = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT, SHIPMENT_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "SMS-notification about shipment status can not be sent");
        assertEquals(exception.getClass(), NotificationException.class);

    }

    @Test
    public void whenEmailNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException() {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(true);

        Throwable exception = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT, SHIPMENT_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "Email-notification about shipment status can not be sent");
        assertEquals(exception.getClass(), NotificationException.class);
    }

    @Test
    public void whenEmailNotificationAboutOrderStatusWasNotSentThanThrowNotificationException()  {
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(true);

        Throwable exception = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(ORDER,ORDER_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "Email-notification about order status can not be sent");
        assertEquals(exception.getClass(), NotificationException.class);
    }

    @Test
    public void whenSMSNotificationAboutOrderStatusWasNotSentThanThrowNotificationException()  {
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(true);

        Throwable exception = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(ORDER, ORDER_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "SMS-notification about order status can not be sent");
        assertEquals(exception.getClass(), NotificationException.class);
    }

    @Test
    public void whenNotFoundPhoneOfMemberThanSMSNotificationAboutShipmentStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberNotFoundException.class, () -> {
            notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT,SHIPMENT_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "There is not found the phone number");
        assertEquals(exception.getClass(), MemberNotFoundException.class);

    }

    @Test
    public void whenNotFoundEmailOfMemberThanEmailNotificationAboutShipmentStatusWillNotBeSentThanThrowMemberNotFoundException() throws NotificationException {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberNotFoundException.class, () -> {
            notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT,SHIPMENT_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "There is not found the email");
        assertEquals(exception.getClass(), MemberNotFoundException.class);
    }


    @Test
    public void whenNotFoundEmailOfMemberThanEmailNotificationAboutOrderStatusWillNotBeSentThanThrowMemberNotFoundException() {
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberNotFoundException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(ORDER, ORDER_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "There is not found the member's email");
        assertEquals(exception.getClass(), MemberNotFoundException.class);
    }

    @Test
    public void whenNotFoundPhoneNumberOfMemberThanSMSNotificationAboutOrderStatusWillNotBeSentThanThrowMemberNotFoundException(){
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberNotFoundException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(ORDER, ORDER_LOG, MEMBER);
        });

        assertEquals(exception.getMessage(), "There is not found the member's phone number");
        assertEquals(exception.getClass(), MemberNotFoundException.class);
    }


    @Test
    public void whenShipmentStatusWasChangedSendSMSNotification() {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(true);

        var resultOrder = notificationService.sendSMSNotificationAboutShipmentStatus(SHIPMENT, SHIPMENT_LOG, MEMBER);

        assertEquals(EXPECTED_MESSAGE_ABOUT_SHIPMENT_STATUS, resultOrder.getContent());
        verify(shippingDao).addShipmentLog(SHIPMENT_LOG_LIST.add(SHIPMENT_LOG));
        verify(shippingDao).updateShipment(SHIPMENT);
    }


    @Test
    public void whenShipmentStatusWasChangedSendEmailNotification()  {
        when(shippingDao.isThisShipmentLogExist(SHIPMENT_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(true);

        var resultOrder = notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT,SHIPMENT_LOG, MEMBER);

        assertEquals(EXPECTED_MESSAGE_ABOUT_SHIPMENT_STATUS, resultOrder.getContent());
        verify(shippingDao).addShipmentLog(SHIPMENT_LOG_LIST.add(SHIPMENT_LOG));
        verify(shippingDao).updateShipment(SHIPMENT);
    }

    @Test
    public void whenOrderStatusWasChangedSendSMSNotification()  {

        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(true);

        var resultOrder = notificationService.sendSMSNotificationAboutOrderStatus(ORDER, ORDER_LOG, MEMBER);

        assertEquals(EXPECTED_MESSAGE_ABOUT_ORDER_STATUS, resultOrder.getContent());
        verify(orderDao).addOrderLog(ORDER.getOrderLog().add(ORDER_LOG));
        verify(orderDao).updateOrder(ORDER);
    }


    @Test
    public void whenOrderStatusWasChangedSendEmailNotification()  {
        when(orderDao.isThisOrderLogExist(ORDER_LOG)).thenReturn(false);
        when(notificationDao.isFoundMemberEmail(MEMBER.getAccount().getEmail())).thenReturn(true);

        var resultOrder = notificationService.sendEmailNotificationAboutOrderStatus(ORDER, ORDER_LOG, MEMBER);

        assertEquals(EXPECTED_MESSAGE_ABOUT_ORDER_STATUS, resultOrder.getContent());
        verify(orderDao).addOrderLog(ORDER.getOrderLog().add(ORDER_LOG));
        verify(orderDao).updateOrder(ORDER);
    }

    private ShipmentLog mockShipmentLog() {
        return new ShipmentLog("777", ShipmentStatus.SHIPPED, LocalDateTime.now());
    }

    private List<ShipmentLog> mockListOfShipmentLog(ShipmentLog shipmentLog) {
        List<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipmentLogs.add(shipmentLog);
        return shipmentLogs;
    }

    private Shipment mockShipment (){
        return new Shipment("777", LocalDateTime.now(), LocalDateTime.now().plusDays(5),"byAir", SHIPMENT_LOG_LIST);
    }

    private OrderLog mockOrderLog() {
        return new OrderLog("888", LocalDateTime.now(), OrderStatus.PENDING);
    }

    private List<OrderLog> mockOrderLogList(OrderLog orderLog){
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(orderLog);
        return orderLogList;
    }

    private Order mockOrder(){
        return new Order("888",OrderStatus.PENDING, LocalDateTime.now(), ORDER_LOG_LIST);
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
                "10101010",
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
