package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.enums.OrderStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderIsAlreadyShippedException;
import com.ra.course.aws.online.shopping.exceptions.OrderLogIsAlreadyExistException;
import com.ra.course.aws.online.shopping.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    private OrderServiceImpl orderService;
    private OrderDao orderDao = mock(OrderDao.class);
    private final String ORDER_IN_DB = "Ref123";
    private final Long MEMBER_ID = 10L;
    private Order searchOrder;
    private Member searchMember;
    private String orderNumber = "101010";

    private final OrderLog ORDER_LOG = mockOrderLog("101010", LocalDateTime.now(), OrderStatus.PENDING);
    private final List<OrderLog> ORDER_LOG_LIST = mockOrderLogList(ORDER_LOG);
    private final Order ORDER = mockOrder("855", OrderStatus.PENDING, LocalDateTime.now(), ORDER_LOG_LIST);

    @BeforeEach
    public void before() {
        orderService = new OrderServiceImpl(orderDao);
        searchOrder = mockOrder(ORDER_IN_DB, OrderStatus.COMPLETE, LocalDateTime.now(), ORDER_LOG_LIST);
        searchMember = mockMember(MEMBER_ID);
        when(orderDao.findByOrderNumber(ORDER.getOrderNumber())).thenReturn(ORDER);
        when(orderDao.findLogListByOrder(ORDER.getOrderLog())).thenReturn(ORDER_LOG_LIST);
    }

    @Test
    public void whenSendForShipmentOrderThanOrderStatusChange() {
        when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(searchOrder);
        orderService.sendForShipment(ORDER);
        assertEquals(ORDER.getStatus(), OrderStatus.SHIPPED);
    }

    @Test
    public void shouldThrowExceptionIfOrderIsAlreadyShipped() {
        var alreadyShippedOrder = mockOrder("8554", OrderStatus.SHIPPED, LocalDateTime.now(), ORDER_LOG_LIST);

        Throwable exception = Assertions.assertThrows(OrderIsAlreadyShippedException.class, () -> {
            orderService.sendForShipment(alreadyShippedOrder);
        });

        assertEquals(exception.getMessage(), "This Order is already shipped");
        assertEquals(exception.getClass(), OrderIsAlreadyShippedException.class);
    }


    @Test
    public void whenAddOrderLogToOrderLogListThenReturnTrue() {
        OrderLog newOrderLog = mockOrderLog("55", LocalDateTime.now().minusDays(1), OrderStatus.PENDING);

        boolean actualResponse = orderService.addOrderLogToOrder(ORDER, newOrderLog);

        assertEquals(actualResponse, true);
    }

    @Test
    public void shouldThrowExceptionIfOrderLogIsAlreadyExist() {

        Throwable exception = Assertions.assertThrows(OrderLogIsAlreadyExistException.class, () -> {
            orderService.addOrderLogToOrder(ORDER, ORDER_LOG);
        });

        assertEquals(exception.getMessage(), "This OrderLog is already exist");
        assertEquals(exception.getClass(), OrderLogIsAlreadyExistException.class);
    }


    @Test
    public void shouldGetOrderTrack() {
        when(orderDao.findByOrderNumber(orderNumber)).thenReturn(ORDER);
        List<OrderLog> expectedResult = orderDao.findLogListByOrder(ORDER.getOrderLog());

        List<OrderLog> actualResponse = orderService.getOrderTrack(orderNumber);

        assertEquals(actualResponse, expectedResult);
    }

    @Test

    public void shouldReturnEmptyListIfOrderNumberNotFound() {
        assertEquals(orderService.getOrderTrack(null), Collections.emptyList());
    }

    @Test()
    public void shouldThrowMemberNotFoundException() {
        var searchInDbMemberID = mockMember(5L);

        when(orderDao.isFoundMemberID(searchInDbMemberID.getMemberID())).thenReturn(false);

        Throwable exception = Assertions.assertThrows(MemberDataNotFoundException.class, () -> {
            orderService.cancelOrder(searchOrder, searchInDbMemberID);
        });

        assertEquals(exception.getMessage(), "There is not found the Member by this ID");
        assertEquals(exception.getClass(), MemberDataNotFoundException.class);
    }

    @Test()
    public void shouldThrowNullPointerException() {
        when(orderDao.isFoundMemberID(searchMember.getMemberID())).thenReturn(true);
        when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(null);

        Throwable exception = Assertions.assertThrows(NullPointerException.class, () -> {
            orderService.cancelOrder(null, searchMember);
        });

        assertEquals(exception.getClass(), NullPointerException.class);
    }

    @Test
    public void whenOrderDateIsAfterCurrentThenOrderCanBeCanceled() {
        var InDbOrder = mockOrder(ORDER_IN_DB, OrderStatus.PENDING, LocalDateTime.now().minusDays(1), ORDER_LOG_LIST);
        when(orderDao.isFoundMemberID(searchMember.getMemberID())).thenReturn(true);
        when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(InDbOrder);
        var resultOrder = orderService.cancelOrder(searchOrder, searchMember);
        Assertions.assertSame(resultOrder.getStatus(), OrderStatus.CANCELED);
        assertEquals(10L, searchMember.getMemberID());
    }

    private Member mockMember(long id) {
        Member member = new Member(new Account());
        member.setMemberID(id);
        return member;
    }


    private OrderLog mockOrderLog(String orderNumber, LocalDateTime creationDate, OrderStatus status) {
        return new OrderLog(orderNumber, creationDate, status);
    }

    private List<OrderLog> mockOrderLogList(OrderLog orderLog) {
        List<OrderLog> orderLogList = new ArrayList<>();
        orderLogList.add(orderLog);
        return orderLogList;
    }

    private Order mockOrder(String orderNumber, OrderStatus status, LocalDateTime orderDate, List<OrderLog> orderLog) {
        return new Order(orderNumber, status, orderDate, orderLog);
    }
}
