package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.dao.AccountDao;
import com.ra.course.aws.online.shopping.dao.ProductDao;
import com.ra.course.aws.online.shopping.dao.ShoppingCartDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.enums.ShipmentStatus;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class NotificationServiceImplIntegrationTest {
    @Primary
    @Bean
    public AccountDao mockedAccountDao() {
        return mock(AccountDao.class);
    }

    @Primary
    @Bean
    public ProductDao mockedProductDao() {
        return mock(ProductDao.class);
    }

    @Primary
    @Bean
    public ShoppingCartDao mockedShoppingCartDao() {
        return mock(ShoppingCartDao.class);
    }

    @Autowired
    private NotificationService notificationService;


    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 22, 11);

    private String emailNotExist = "hhhhh@gmail.com";
    private String phoneNotExist = "456522";

    private String emailExist = "111j@gmail.com";
    private String phoneExist = "38012345111";

    private Address address = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");

    private Account accountWithNotExistData = makeAccount(emailNotExist, phoneNotExist);
    private Member memberWithNotExistData = makeMember(accountWithNotExistData);

    private Account accountExist = makeAccount(emailExist, phoneExist);
    private Member memberExist = makeMember(accountExist);

    //OrderLog newOrderLog = makeOrderLog(7, "5548541", LocalDateTime.now().minusDays(1), OrderStatus.PENDING);
    //ShipmentLog newShipmentLog = makeShipmentLog(7, "55785", ShipmentStatus.ONHOLD, LocalDateTime.now().minusDays(1));

    OrderLog newOrderLog = new OrderLog(55555555,"5548541", LocalDateTime.now(), OrderStatus.PENDING);
    ShipmentLog newShipmentLog = new ShipmentLog( 8555555,"55785", ShipmentStatus.ONHOLD, LocalDateTime.now().minusDays(1));

    OrderLog orderLogInDB = makeOrderLog(1, "1", time, OrderStatus.PENDING);
    ShipmentLog shipmentLogInDB = makeShipmentLog(1, "1", ShipmentStatus.SHIPPED, time);

    //work correct
    @Test
    public void whenNotificationAboutShipmentStatusWasNotSentThanThrowNotificationException() {

        Throwable exceptionEmail = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutShipmentStatus(shipmentLogInDB, memberExist);
        });

        Throwable exceptionSMS = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutShipmentStatus(shipmentLogInDB, memberExist);
        });

        assertEquals(exceptionEmail.getMessage(), "Email-notification about shipment status can not be sent");
        assertEquals(exceptionEmail.getClass(), NotificationException.class);

        assertEquals(exceptionSMS.getMessage(), "SMS-notification about shipment status can not be sent");
        assertEquals(exceptionSMS.getClass(), NotificationException.class);
    }

    //work correct
    @Test
    public void whenNotificationAboutOrderStatusWasNotSentThanThrowNotificationException() {

        Throwable exceptionEmail = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(orderLogInDB, memberExist);
        });

        Throwable exceptionSMS = Assertions.assertThrows(NotificationException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(orderLogInDB, memberExist);
        });

        assertEquals(exceptionSMS.getMessage(), "SMS-notification about order status can not be sent");
        assertEquals(exceptionSMS.getClass(), NotificationException.class);

        assertEquals(exceptionEmail.getMessage(), "Email-notification about order status can not be sent");
        assertEquals(exceptionEmail.getClass(), NotificationException.class);
    }

    //work correct
    @Test
    public void whenShipmentStatusWasChangedSendSMSNotification() {

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            notificationService.sendSMSNotificationAboutOrderStatus(newOrderLog, memberWithNotExistData);
            notificationService.sendSMSNotificationAboutShipmentStatus(newShipmentLog, memberWithNotExistData);
        });

        assertEquals(exception.getMessage(), "There is not found the phone number");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    //work correct
    @Test
    public void whenMemberPhoneNotFoundThenExceptionThrownMemberNotFoundException() {

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(newOrderLog, memberWithNotExistData);
            notificationService.sendEmailNotificationAboutShipmentStatus(newShipmentLog, memberExist);
        });

        assertEquals(exception.getMessage(), "There is not found the member's email");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    //work correct
    @Test
    public void whenShipmentStatusWasChangedSendNotification() {
        ShipmentLog newShipmentLog = new ShipmentLog(11144555,"55785", ShipmentStatus.ONHOLD, LocalDateTime.now());
        String expectedResult = "your shipment number 55785 has changed status on ONHOLD";

        var resultSMS = notificationService.sendSMSNotificationAboutShipmentStatus(newShipmentLog, memberExist);
        var resultEmail = notificationService.sendEmailNotificationAboutShipmentStatus(newShipmentLog, memberExist);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }

    //work correct
    @Test
    public void whenOrderStatusWasChangedSendNotification() {
        OrderLog newOrderLog = new OrderLog(855514477,"5548541", LocalDateTime.now(), OrderStatus.PENDING);
        String expectedResult = "your order number 5548541 has changed status on PENDING";

        var resultSMS = notificationService.sendSMSNotificationAboutOrderStatus(newOrderLog, memberExist);
        var resultEmail = notificationService.sendEmailNotificationAboutOrderStatus(newOrderLog, memberExist);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }

    private ShipmentLog makeShipmentLog(long id, String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(id, shipmentNumber, status, creationDate);
    }

    private OrderLog makeOrderLog(long id, String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(id, orderNumber, creationDate, status);
    }

    private Account makeAccount(String email, String phoneNumber) {
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(new CreditCard("VISA", "77", 44, address));
        List<ElectronicBankTransfer> electronicBankTransfers = new ArrayList<>();
        electronicBankTransfers.add(new ElectronicBankTransfer("P8", "77", "10"));
        Account account = new Account(
                "Ivan",
                "1",
                AccountStatus.ACTIVE,
                "Ann",
                address,
                email,
                phoneNumber,
                creditCardList,
                electronicBankTransfers
        );
        return account;
    }


    private Member makeMember(Account account) {
        Member member = new Member(account);
        return member;
    }
}
