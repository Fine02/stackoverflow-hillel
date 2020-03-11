package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderStatus;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.OrderNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    private OrderServiceImpl orderService;
    private OrderDao orderDao = mock(OrderDao.class);
    private final String ORDER_IN_DB = "Ref123";
    private final Long MEMBER_ID_IN_DB = 10L;
    private Exception exception;


    @BeforeEach
    public void before() {
        orderService = new OrderServiceImpl(orderDao);
    }

    @Test()
    public void shouldThrowMemberNotFoundException() {
        var searchOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now());
        var InDbOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now().minusDays(1));
        var searchInDbMemberID = mockMember(5L);
        try {
            when(orderDao.isFoundMemberID(searchInDbMemberID.getMemberID())).thenReturn(false);
            when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(InDbOrder);
            orderService.cancelOrder(searchOrder, searchInDbMemberID);
        } catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof MemberNotFoundException);
        assertEquals("There is not found the Member by this ID", exception.getMessage());
    }

    @Test()
    public void shouldThrowOrderNotFoundException(){
        var searchOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now());
        var InDbOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now().plusDays(8));
        var searchInDbMemberID = mockMember(5L);
        try {
            when(orderDao.isFoundMemberID(searchInDbMemberID.getMemberID())).thenReturn(true);
            when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(InDbOrder);
            orderService.cancelOrder(searchOrder, searchInDbMemberID);
        } catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof OrderNotFoundException);
        assertEquals("There is not found the Order by this number", exception.getMessage());
    }

    @Test
    public void whenOrderDateIsAfterCurrentThenOrderCanBeCanceled() throws MemberNotFoundException, OrderNotFoundException {
        var searchOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now());
        var InDbOrder = mockOrder(ORDER_IN_DB, LocalDateTime.now().minusDays(1));
        var searchInDbMemberID = mockMember(MEMBER_ID_IN_DB);
        when(orderDao.isFoundMemberID(searchInDbMemberID.getMemberID())).thenReturn(true);
        when(orderDao.findByOrderNumber(ORDER_IN_DB)).thenReturn(InDbOrder);
        var resultOrder = orderService.cancelOrder(searchOrder, searchInDbMemberID);
        Assertions.assertSame(resultOrder.getStatus(), OrderStatus.CANCELED);
        assertEquals(10L, searchInDbMemberID.getMemberID());
    }


    private Order mockOrder(String orderNum, LocalDateTime dateTime) {
        return new Order(orderNum, OrderStatus.PENDING, dateTime);
    }

    private Member mockMember(long id) {
        Member member = new Member(new Account());
        member.setMemberID(id);
        return member;
    }
}
