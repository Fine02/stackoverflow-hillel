package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcOrderDaoImpl implements OrderDao {
    public static final String UPDATE_ORDER = "UPDATE `order` SET  order_status_id=? WHERE orderNumber=?";
    public static final String GET_ID_O_STATUS = "SELECT os.id FROM order_status os WHERE os.status=?";
    public static final String FIND_ORDER_LOG = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status, ol.order_id FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    public static final String GET_OLOG_BY_ID = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    public static final String INSERT_ORDER_LOG = "INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES (?, ?, ?, ?)";
    public static final String GET_ORDER_ID = "SELECT id FROM `order`  WHERE orderNumber=?";

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

    public static final String GET_ORDER = "SELECT \n" +
            "o.id, o.orderNumber, os2.status, o.orderDate\n" +
            "FROM  `order` o \n" +
            "JOIN order_status os2 ON o.order_status_id = os2.id\n" +
            "WHERE \n" +
            "o.orderNumber =?";

    public static final String GET_ORDER_LOG = "SELECT ol.id, ol.orderNumber,\n" +
            "ol.creationDate, os.status\n" +
            "FROM  order_log ol\n" +
            "JOIN order_status os ON ol.order_status_id = os.id WHERE \n" +
            "ol.orderNumber =?";

    private transient final JdbcTemplate jdbcTemplate;
    private transient final GetLastIdRowMapper getId;
    private transient final OrderLogRowMapper orderLog;
    private transient final OrderRowMapper orderRowMapper;
    private transient final BooleanOrderLogRowMapper booleanLogMapper;
    private transient final MemberBooleanRowMapper memberRowMapper;

    @Autowired
    public JdbcOrderDaoImpl(final JdbcTemplate jdbcTemplate, final GetLastIdRowMapper getId, final OrderLogRowMapper orderLog, final OrderRowMapper orderRowMapper, final BooleanOrderLogRowMapper booleanLogMapper, final MemberBooleanRowMapper memberRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.getId = getId;
        this.orderLog = orderLog;
        this.orderRowMapper = orderRowMapper;
        this.booleanLogMapper = booleanLogMapper;
        this.memberRowMapper = memberRowMapper;
    }

    @Override
    public void updateOrder(final Order order) {
        final Integer getIdOfOrder = jdbcTemplate.queryForObject(GET_ORDER_ID, getId, order.getOrderNumber());
        final Integer getStatusNumber = jdbcTemplate.queryForObject(GET_ID_O_STATUS, getId, order.getStatus().toString());
        jdbcTemplate.update(UPDATE_ORDER, getStatusNumber, order.getOrderNumber());
        jdbcTemplate.update(INSERT_ORDER_LOG, order.getOrderNumber(), LocalDateTime.now(), getStatusNumber, getIdOfOrder);
    }

    @Override
    public void addOrderLogAndUpdateOrder(final Order order, final OrderLog orderLog) {
        final Integer getIdOfOrder = jdbcTemplate.queryForObject(GET_ORDER_ID, getId, orderLog.getOrderNumber());
        final Integer getNumberOfStatus = jdbcTemplate.queryForObject(GET_ID_O_STATUS, getId, orderLog.getStatus().toString());
        jdbcTemplate.update(UPDATE_ORDER, getNumberOfStatus, order.getOrderNumber());
        jdbcTemplate.update(INSERT_ORDER_LOG, orderLog.getOrderNumber(), orderLog.getCreationDate(), getNumberOfStatus, getIdOfOrder);
    }

    @Override
    public boolean isFoundMemberID(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_MEMBER_BY_ID, memberRowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public Order findByOrderNumber(final String orderNumber) {
        try {
            final Order result = jdbcTemplate.queryForObject(GET_ORDER, orderRowMapper, orderNumber);
            if (result != null) {
                final List<OrderLog> list = jdbcTemplate.query(GET_ORDER_LOG, orderLog, orderNumber);
                result.setOrderLog(list);
            }
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<OrderLog> findLogListByOrder(final List<OrderLog> orderLogList) {
        final OrderLog orderLog = orderLogList.get(0);
        final String orderNumber = orderLog.getOrderNumber();
        return jdbcTemplate.query(GET_ORDER_LOG, this.orderLog, orderNumber);
    }

    @Override
    public boolean isThisOrderLogExist(final OrderLog orderLog) {
        try {
            if (orderLog != null) {
                final Long foundId = orderLog.getId();
                return jdbcTemplate.queryForObject(FIND_ORDER_LOG, booleanLogMapper, foundId);
            }
            return false;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public OrderLog findOrderLogById(final Long orderLogId) {
        try {
            return jdbcTemplate.queryForObject(GET_OLOG_BY_ID, orderLog, orderLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
