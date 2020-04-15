package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.AwsOnlineShoppingApplication;
import com.ra.course.aws.online.shopping.TestConfig;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.enums.AccountStatus;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.payment.CreditCard;
import com.ra.course.aws.online.shopping.entity.payment.ElectronicBankTransfer;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderIsAlreadyShippedException;
import com.ra.course.aws.online.shopping.exceptions.OrderLogIsAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AwsOnlineShoppingApplication.class, TestConfig.class})
@ActiveProfiles("local")
public class OrderServiceImplIntegrationTest {

    @Autowired
    private OrderService orderService;
    Address address = new Address("Mira, 8", "Kyiv", "Kyiv", "14004", "Ukraine");
    LocalDateTime time = LocalDateTime.of(2020, 3, 19, 22, 25, 25);
    LocalDateTime time2 = LocalDateTime.of(2020, 3, 20, 22, 22, 11);
    LocalDateTime time3 = LocalDateTime.of(2020, 3, 21, 22, 22, 11);

    Order order = new Order("3", OrderStatus.UNSHIPPED, time);
    Order orderNotExist = new Order("122113", OrderStatus.UNSHIPPED, time);

    public OrderLog orderLog1 = new OrderLog("2", time2, OrderStatus.PENDING);
    public OrderLog orderLog2 = new OrderLog("2", time3, OrderStatus.PENDING);
    public OrderLog orderLog3 = new OrderLog("3", LocalDateTime.now(), OrderStatus.COMPLETE);
    public OrderLog existOrderLogInDB = new OrderLog("3", time3, OrderStatus.COMPLETE);

    Order orderIsAlreadyShipped = new Order("3", OrderStatus.SHIPPED, time);
    private Member memberExist = makeMember(makeAccount(), 1L);
    private Member memberNotExist = makeMember(makeAccount(), 156622L);

    //work correct
    @Test
    public void whenSendForShipmentOrderThanOrderStatusChange() {
        orderService.sendForShipment(order);

        assertEquals(order.getStatus(), OrderStatus.SHIPPED);
    }

    //work correct
    @Test
    public void shouldThrowExceptionIfOrderIsAlreadyShipped() {
        Throwable exception = Assertions.assertThrows(OrderIsAlreadyShippedException.class, () -> {
            orderService.sendForShipment(orderIsAlreadyShipped);
        });

        assertEquals(exception.getMessage(), "This Order is already shipped");
        assertEquals(exception.getClass(), OrderIsAlreadyShippedException.class);
    }

    //work correct
    @Test
    public void shouldGetOrderTrack() {
        String orderNumber = "2";
        List<OrderLog> expectedResult = makeOrderLogList();

        List<OrderLog> actualResponse = orderService.getOrderTrack(orderNumber);

        assertEquals(actualResponse, expectedResult);
    }

    //work correct
    @Test
    public void shouldReturnEmptyListIfOrderNumberNotFound() {
        String notExistOrderNumber = "102012";
        assertEquals(orderService.getOrderTrack(notExistOrderNumber), Collections.emptyList());
    }

    //work correct
    @Test()
    public void shouldThrowMemberNotFoundException() {
        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            orderService.cancelOrder(order, memberNotExist);
        });

        assertEquals(exception.getMessage(), "There is not found the Member by this ID");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    //work correct
    @Test()
    public void shouldThrowNullPointerException() {

        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            orderService.cancelOrder(orderNotExist, memberExist);
        });

        assertEquals(exception.getClass(), NullPointerException.class);
    }

    //work correct
    @Test
    public void whenOrderDateIsAfterCurrentThenOrderCanBeCanceled() {
        var resultOrder = orderService.cancelOrder(order, memberExist);

        Assertions.assertSame(resultOrder.getStatus(), OrderStatus.CANCELED);

        assertEquals(1L, memberExist.getMemberID());
    }

    //work correct
    @Test
    public void whenAddOrderLogToOrderLogListThenReturnTrue() {
        boolean actualResponse = orderService.addOrderLogToOrder(order, orderLog3);

        assertEquals(actualResponse, true);
    }

    //work correct
    @Test
    public void shouldThrowExceptionIfOrderLogIsAlreadyExist() {

        Throwable exception = Assertions.assertThrows(OrderLogIsAlreadyExistException.class, () -> {
            orderService.addOrderLogToOrder(order, existOrderLogInDB);
        });

        assertEquals(exception.getMessage(), "This OrderLog is already exist");
        assertEquals(exception.getClass(), OrderLogIsAlreadyExistException.class);
    }

    public List<OrderLog> makeOrderLogList() {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(orderLog1);
        orderLogList.add(orderLog2);
        return orderLogList;
    }

    private Account makeAccount() {
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
                "111j@gmail.com",
                "38012345111",
                creditCardList,
                electronicBankTransfers
        );
        return account;
    }

    private Member makeMember(Account account, Long id) {
        Member member = new Member();
        member.setAccount(account);
        member.setMemberID(id);
        return member;
    }

}
