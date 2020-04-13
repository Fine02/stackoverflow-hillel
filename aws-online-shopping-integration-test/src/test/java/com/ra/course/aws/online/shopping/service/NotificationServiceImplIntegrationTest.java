package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.dao.OrderDao;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class NotificationServiceImplIntegrationTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private NotificationService notificationService;


    LocalDateTime time1 = LocalDateTime.of(2020, 3, 19, 22, 25, 25);
    LocalDateTime time2 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);
//    private OrderLog makeOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
//        return new OrderLog(orderNumber, creationDate, status);
//    }
//    private OrderLog makeOrderLog(long id, String orderNumber, LocalDateTime creationDate, OrderStatus status) {
//        return new OrderLog(id, orderNumber, creationDate, status);
//    }
//
//    public OrderLog ORDER_LOG1 = makeOrderLog("2", time1, OrderStatus.PENDING);
//    public OrderLog ORDER_LOG2 = makeOrderLog("2", time2, OrderStatus.PENDING);
//    public OrderLog ORDER_LOG3 = makeOrderLog("2", time3, OrderStatus.PENDING);
//    public OrderLog ORDER_LOG4 = makeOrderLog(1L,"2", time3, OrderStatus.PENDING);

    private final OrderLog orderLog = makeOrderLog("101010", time1, OrderStatus.PENDING);
    private final ShipmentLog shipmentLog = makeShipmentLog("101010", ShipmentStatus.SHIPPED, time1);

    private String emailNotExist = "hhhhh@gmail.com";
    private String phoneNotExist = "456522";
    private String emailExist = "111j@gmail.com";
    private String phoneExist = "38012345111";

    private Address address = new Address("Mira", "Kiyv", "Kyiv", "04114", "Ukraine");

    private Account accountNotExist = makeAccount(emailNotExist, phoneNotExist);
    private Member memberNotExist = makeMember(accountNotExist);

    private Account accountExist = makeAccount(emailExist, phoneExist);
    private Member memberExist = makeMember(accountExist);


    //work not correct
    @Test
    public void whenMemberPhoneNotFoundThenExceptionThrownMemberNotFoundException() {
        OrderLog newOrderLog = makeOrderLog("5548541", LocalDateTime.now().minusDays(1), OrderStatus.PENDING);
        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            notificationService.sendEmailNotificationAboutOrderStatus(newOrderLog, memberNotExist);
            // notificationService.sendEmailNotificationAboutShipmentStatus(SHIPMENT_LOG, memberExist);
        });

        assertEquals(exception.getMessage(), "There is not found the member's email");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    //work correct
    @Test
    public void whenShipmentStatusWasChangedSendNotification() {

        ShipmentLog newShipmentLog = makeShipmentLog("55785", ShipmentStatus.ONHOLD, LocalDateTime.now().minusDays(1));
        String expectedResult = "your shipment number 55785 has changed status on ONHOLD";

        var resultSMS = notificationService.sendSMSNotificationAboutShipmentStatus(newShipmentLog, memberExist);
        var resultEmail = notificationService.sendEmailNotificationAboutShipmentStatus(newShipmentLog, memberExist);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }


    //work correct
    @Test
    public void whenMemberPhoneFoundAndOrderLogIsAbsentInDBThenReturnEmailNotificationAndSMSNotification() {
        OrderLog newOrderLog = makeOrderLog("5548541", LocalDateTime.now().minusDays(1), OrderStatus.PENDING);
        String expectedResult = "your order number 5548541 has changed status on PENDING";

        var resultSMS = notificationService.sendSMSNotificationAboutOrderStatus(newOrderLog, memberExist);
        var resultEmail = notificationService.sendEmailNotificationAboutOrderStatus(newOrderLog, memberExist);

        assertEquals(expectedResult, resultSMS.getContent());
        assertEquals(expectedResult, resultEmail.getContent());
    }

    private ShipmentLog makeShipmentLog(String shipmentNumber, ShipmentStatus status, LocalDateTime creationDate) {
        return new ShipmentLog(shipmentNumber, status, creationDate);
    }

    private OrderLog makeOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
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
