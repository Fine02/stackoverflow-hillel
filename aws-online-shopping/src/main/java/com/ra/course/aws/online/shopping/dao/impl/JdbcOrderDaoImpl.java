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
    private final MemberBooleanRowMapper memberBooleanRowMapper;
    private final BooleanOrderLogRowMapper booleanOrderLogRowMapper;
    private final GetLastIdRowMapper getIdRowMapper;
    private final OrderRowMapper orderRowMapper;
    private final OrderLogRowMapper orderLogRowMapper;

    @Autowired
    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate, MemberBooleanRowMapper memberBooleanRowMapper, BooleanOrderLogRowMapper booleanOrderLogRowMapper, GetLastIdRowMapper getIdRowMapper, OrderRowMapper orderRowMapper, OrderLogRowMapper orderLogRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberBooleanRowMapper = memberBooleanRowMapper;
        this.booleanOrderLogRowMapper = booleanOrderLogRowMapper;
        this.getIdRowMapper = getIdRowMapper;
        this.orderRowMapper = orderRowMapper;
        this.orderLogRowMapper = orderLogRowMapper;
    }

    public static final String UPDATE_ORDER_BY_ORDER_NUMBER = "UPDATE \"order\" SET  order_status_id=? WHERE orderNumber=?";
    public static final String GET_ID_OF_ORDER_STATUS = "SELECT os.id FROM order_status os WHERE os.status=?";
    public static final String FIND_ORDER_LOG_BY_FIELDS = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status, ol.order_id FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    public static final String GET_ORDER_LOG_BY_ID = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    public static final String INSERT_ORDER_LOG = "INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES (?, ?, ?, ?)";
    public static final String GET_ORDER_ID = "SELECT id FROM \"order\"  WHERE orderNumber=?";

    public static final String GET_MEMBER_BY_ID = " SELECT \n" +
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

    public static final String GET_ORDER_BY_ORDER_NUMBER = "SELECT \n" +
            "o.id, o.orderNumber, os2.status, o.orderDate\n" +
            "FROM  \"order\" o \n" +
            "JOIN order_status os2 ON o.order_status_id = os2.id\n" +
            "WHERE \n" +
            "o.orderNumber =?";

    public static final String GET_ORDER_LOG_BY_ORDER_NUMBER = "SELECT \n" +
            "ol.id, ol.orderNumber,\n" +
            "ol.creationDate, os.status\n" +
            "FROM  order_log ol\n" +
            "JOIN order_status os ON ol.order_status_id = os.id\n" +
            "WHERE \n" +
            "ol.orderNumber =?";

    @Override
    public void updateOrder(Order order) {
        Integer getIdOfOrder = jdbcTemplate.queryForObject(GET_ORDER_ID, getIdRowMapper, order.getOrderNumber());
        Integer getNumberFromStatus = jdbcTemplate.queryForObject(GET_ID_OF_ORDER_STATUS, getIdRowMapper, order.getStatus().toString());
        jdbcTemplate.update(UPDATE_ORDER_BY_ORDER_NUMBER, getNumberFromStatus, order.getOrderNumber());
        jdbcTemplate.update(INSERT_ORDER_LOG, order.getOrderNumber(), LocalDateTime.now(), getNumberFromStatus, getIdOfOrder);
    }

    @Override
    public void addOrderLogAndUpdateOrder(Order order, OrderLog orderLog) {
        Integer getIdOfOrder = jdbcTemplate.queryForObject(GET_ORDER_ID, getIdRowMapper, orderLog.getOrderNumber());
        Integer getNumberOfStatus = jdbcTemplate.queryForObject(GET_ID_OF_ORDER_STATUS, getIdRowMapper, orderLog.getStatus().toString());
        jdbcTemplate.update(UPDATE_ORDER_BY_ORDER_NUMBER, getNumberOfStatus, order.getOrderNumber());
        jdbcTemplate.update(INSERT_ORDER_LOG, orderLog.getOrderNumber(), orderLog.getCreationDate(), getNumberOfStatus, getIdOfOrder);
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        try {
            var result = jdbcTemplate.queryForObject(GET_MEMBER_BY_ID, memberBooleanRowMapper, id);
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }catch (NullPointerException ex) {
            return false;
        }
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        try {
            List<OrderLog> list = jdbcTemplate.query(GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber);
            Order result = jdbcTemplate.queryForObject(GET_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber);
            Order order = result;
            order.setOrderLog(list);
            return order;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        OrderLog orderLog = orderLogList.get(0);
        String orderNumber = orderLog.getOrderNumber();
        List<OrderLog> orderLogsList = jdbcTemplate.query(GET_ORDER_LOG_BY_ORDER_NUMBER, orderLogRowMapper, orderNumber);
        return orderLogsList;
    }

    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
        try {
            Long foundId = orderLog.getId();
            return jdbcTemplate.queryForObject(FIND_ORDER_LOG_BY_FIELDS, booleanOrderLogRowMapper, foundId);
        } catch (NullPointerException ex) {
            return false;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        try {
            return jdbcTemplate.queryForObject(GET_ORDER_LOG_BY_ID, orderLogRowMapper, orderLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
