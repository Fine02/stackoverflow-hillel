package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcOrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;
    private final ListOrderLogRowMapper orderLogRowMapper;
    private final OrderLogRowMapper orderLogOneRowMapper;
    private final MemberBooleanRowMapper memberBooleanRowMapper;
    private final BooleanOrderLogRowMapper booleanOrderLogRowMapper;
    private final NumberFromStatusRowMapper numberFromStatusRowMapper;
    private final OrderRowMapper2 orderRowMapper2;
    private final GetLastIdRowMapper getIdRowMapper;

    @Autowired
    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper, ListOrderLogRowMapper orderLogRowMapper, OrderLogRowMapper orderLogOneRowMapper, MemberRowMapper memberRowMapper, MemberBooleanRowMapper memberBooleanRowMapper, BooleanOrderLogRowMapper booleanOrderLogRowMapper, NumberFromStatusRowMapper numberFromStatusRowMapper, OrderRowMapper2 orderRowMapper2, GetLastIdRowMapper getIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
        this.orderLogRowMapper = orderLogRowMapper;
        this.orderLogOneRowMapper = orderLogOneRowMapper;
        this.memberBooleanRowMapper = memberBooleanRowMapper;
        this.booleanOrderLogRowMapper = booleanOrderLogRowMapper;
        this.numberFromStatusRowMapper = numberFromStatusRowMapper;
        this.orderRowMapper2 = orderRowMapper2;
        this.getIdRowMapper = getIdRowMapper;
    }

    private static final String UPDATE_ORDER_BY_ORDERNUMBER = "UPDATE \"order\" SET  order_status_id=?, orderDate=? WHERE orderNumber=?";

    private static final String GET_INT_OF_ORDER_STATUS = "SELECT os.id FROM order_status os WHERE os.status=?";

    private static final String GET_MEMBER_BY_ID = " SELECT \n" +
            "\t    m.account_id,\n" +
            "        m.id member_id,\n" +
            "        a.userName, a.password,\n" +
            "        acs.status,\n" +
            "        a.name,\n" +
            "        adr.streetAddress, adr.city, adr.state, adr.zipcode, adr.country,\n" +
            "        a.email,\n" +
            "        a.phone,\n" +
            "        crc.nameOnCard, crc.cardNumber,crc.code, \n" +
            "        ebt.bankName, ebt.routingNumber, ebt.accountNumber\n" +
            "FROM member m JOIN account a ON m.account_id= a.id\n" +
            "JOIN account_status acs ON a.account_status_id = acs.id\n" +
            "JOIN address adr ON a.address_id = adr.id\n" +
            "JOIN credit_card crc ON crc.account_id = a.id\n" +
            "JOIN electronic_bank_transfer ebt ON ebt.account_id = a.id\n" +
            "WHERE m.id=? ";

    //немного неверный результат т.к. ищит то он в другой таблице
//    private static final String FIND_ORDER_BY_ORDER_NUMBER = "SELECT \n" +
//            "o.id order_id, o.orderNumber, os2.status order_status, o.orderDate,\n" +
//            "ol.id orderLog_id, ol.orderNumber,\n" +
//            "ol.creationDate, os.status orderLogStatus\n" +
//            "FROM  (order_log ol\n" +
//            "JOIN \"order\" o ON ol.order_id = o.id)\n" +
//            ",order_status os, order_status os2\n" +
//            "WHERE (\n" +
//            "ol.orderNumber =? AND ol.order_status_id = os.id AND o.order_status_id = os2.id\n" +
//            ")";


    private static final String GET_ORDER_BY_ORDER_NUMBER = "SELECT \n" +
            "o.id, o.orderNumber, os2.status, o.orderDate\n" +
            "FROM  \"order\" o \n" +
            "JOIN order_status os2 ON o.order_status_id = os2.id\n" +
            "WHERE \n" +
            "o.orderNumber =?";

    private static final String GET_ORDER_LOGS_BY_ORDER_NUMBER = "SELECT \n" +
            "ol.id, ol.orderNumber,\n" +
            "ol.creationDate, os.status\n" +
            "FROM  order_log ol\n" +
            "JOIN order_status os ON ol.order_status_id = os.id\n" +
            "WHERE \n" +
            "ol.orderNumber =?";


    private static final String GET_ORDER_LOGS_BY_ORDER_NO_IN_OLOGS = "SELECT \n" +
            "ol.id orderLog_id, ol.orderNumber,\n" +
            "ol.creationDate, os.status orderLogStatus\n" +
            "FROM  (order_log ol\n" +
            "JOIN \"order\" o ON ol.order_id = o.id)\n" +
            ",order_status os, order_status os2\n" +
            "WHERE (\n" +
            "ol.order_status_id = os.id AND o.order_status_id = os2.id AND ol.orderNumber=? \n" +
            ") ";

    private static final String FIND_ORDER_LOG_BY_FIELDS = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status, ol.order_id FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";

    private static final String ADD_ORDER_LOG = "INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES (?, ?, ?, ?)";

    private static final String GET_ORDER_LOG_BY_ID = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    private static final String INSERT_ORDER_LOG = "INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES (?, ?, ?, ?)";
    private static final String GET_ORDER_ID ="SELECT id FROM \"order\"  WHERE orderNumber=?";

    //work correct
    @Override
    public void updateOrder(Order order) {
        Integer getIdOfOrder = jdbcTemplate.queryForObject(GET_ORDER_ID, getIdRowMapper, order.getOrderNumber());
        Integer getNumberFromStatus = jdbcTemplate.queryForObject(GET_INT_OF_ORDER_STATUS, getIdRowMapper, order.getStatus().toString());
        jdbcTemplate.update(UPDATE_ORDER_BY_ORDERNUMBER, getNumberFromStatus, LocalDateTime.now(), order.getOrderNumber());
        jdbcTemplate.update(INSERT_ORDER_LOG, order.getOrderNumber(), LocalDateTime.now(), getNumberFromStatus, getIdOfOrder);
    }

    //work correct
    @Override
    public boolean isFoundMemberID(Long id) {
        var result = jdbcTemplate.queryForObject(GET_MEMBER_BY_ID, memberBooleanRowMapper, id);
        return result;
    }

    //work correct
    @Override
    public Order findByOrderNumber(String orderNumber) {
        List<OrderLog> list = jdbcTemplate.query(GET_ORDER_LOGS_BY_ORDER_NUMBER, BeanPropertyRowMapper.newInstance(OrderLog.class), orderNumber);
        Order result = jdbcTemplate.queryForObject(GET_ORDER_BY_ORDER_NUMBER, BeanPropertyRowMapper.newInstance(Order.class), orderNumber);
        Order order =result;
        order.setOrderLog(list);
        return order;
        }

    //work correct?
//    @Override
//    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
//        OrderLog orderLog = orderLogList.get(0);
//        String orderNumber = orderLog.getOrderNumber();
//        List<OrderLog> orderLogsList = jdbcTemplate.query(GET_ORDER_LOGS_BY_ORDER_NO_IN_OLOGS, orderLogOneRowMapper, orderNumber);
//        return orderLogsList;
//    }

    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        OrderLog orderLog = orderLogList.get(0);
        String orderNumber = orderLog.getOrderNumber();
        List<OrderLog> orderLogsList = jdbcTemplate.query(GET_ORDER_LOGS_BY_ORDER_NUMBER, BeanPropertyRowMapper.newInstance(OrderLog.class), orderNumber);
        return orderLogsList;
    }

    //work correct
    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
        try {
            Long foundId = orderLog.getId();
            return jdbcTemplate.queryForObject(FIND_ORDER_LOG_BY_FIELDS, booleanOrderLogRowMapper, foundId);
        } catch (NullPointerException ex) {
            return false;
        }
    }

    //need to be revision for next action
    @Override
    public void addOrderLog(boolean add) {
        // jdbcTemplate.update( BeanPropertyRowMapper.newInstance(OrderLog.class), add);
    }

    //work correct
    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        try {
            return jdbcTemplate.queryForObject(GET_ORDER_LOG_BY_ID, BeanPropertyRowMapper.newInstance(OrderLog.class), orderLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
