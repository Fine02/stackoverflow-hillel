package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Component
public class JdbcOrderDaoImpl implements OrderDao {
    private static final String UPDATE_ORDER_BY_ORDERNUMBER = "UPDATE `ORDER` o SET `orderNumber`=?, `order_status_id`=?, `orderDate`=? WHERE o.orderNumber=?";

    private static final String GET_MEMBER_BY_ID = " SELECT \n" +
            "\tm.`account_id` ,\n" +
            "        m.`id` `member_id`,\n" +
            "        a.`userName`, a.`password`,\n" +
            "        acs.`status`,\n" +
            "        a.`name`,\n" +
            "        adr.`streetAddress`, adr.`city`, adr.`state`, adr.`zipcode`, adr.`country`,\n" +
            "        a.`email`,\n" +
            "        a.`phone`,\n" +
            "        crc.`nameOnCard`, crc.`cardNumber`,crc.`code`, \n" +
            "        ebt.`bankName`, ebt.`routingNumber`, ebt.`accountNumber`\n" +
            "FROM member m JOIN account a ON m.`account_id`= a.`id`\n" +
            "JOIN account_status acs ON a.`account_status_id` = acs.`id`\n" +
            "JOIN `address` adr ON a.`address_id` = adr.`id`\n" +
            "JOIN `credit_card` crc ON crc.`account_id` = a.`id`\n" +
            "JOIN `electronic_bank_transfer` ebt ON ebt.`account_id` = a.`id`\n" +
            "WHERE m.id=1 GROUP BY a.id\n";

//    private static final String FIND_ORDER_BY_ORDER_NUMBER = "SELECT \n" +
//            "o.id order_id, o.orderNumber, os2.status order_status, o.orderDate,\n" +
//            "ol.`id` orderLog_id, ol.`orderNumber`,\n" +
//            "ol.`creationDate`, os.`status` orderLogStatus\n" +
//            "FROM  (`order_log` ol\n" +
//            "JOIN `order` o ON ol.`order_id` = o.`id`)\n" +
//            ",`order_status` os, `order_status` os2\n" +
//            "WHERE (\n" +
//            "ol.`orderNumber` =? AND ol.`order_status_id` = os.`id` AND o.`order_status_id` = os2.`id`\n" +
//            ")";

    private static final String FIND_ORDER_BY_ORDER_NUMBER = "SELECT \n" +
            "o.id order_id, o.orderNumber, os2.status order_status, o.orderDate,\n" +
            "ol.id orderLog_id, ol.orderNumber,\n" +
            "ol.creationDate, os.status orderLogStatus\n" +
            "FROM  (order_log ol\n" +
            "JOIN \"order\" o ON ol.order_id = o.id)\n" +
            ",order_status os, order_status os2\n" +
            "WHERE (\n" +
            "ol.orderNumber =? AND ol.order_status_id = os.id AND o.order_status_id = os2.id\n" +
            ")";

    private static final String GET_ORDER_LOGS_BY_ORDER_NO_IN_OLOGS = "SELECT \n" +
            "ol.`id` orderLog_id, ol.`orderNumber`,\n" +
            "ol.`creationDate`, os.`status` orderLogStatus\n" +
            "FROM  (`order_log` ol\n" +
            "JOIN `order` o ON ol.`order_id` = o.`id`)\n" +
            ",`order_status` os, `order_status` os2\n" +
            "WHERE (\n" +
            "ol.`order_status_id` = os.`id` AND o.`order_status_id` = os2.`id`AND ol.`orderNumber`=? \n" +
            ") ";

    private static final String FIND_ORDER_LOG_BY_FIELDS = "SELECT ol.`id`, ol.`orderNumber`, ol.`creationDate`, os.`status`, ol.`order_id` FROM order_log ol JOIN order_status os ON ol.`order_status_id` = os.`id` WHERE ol.`orderNumber`=? && ol.`creationDate`=? && os.`status`=?";
    private static final String ADD_ORDER_LOG = "INSERT INTO `order_log` (`orderNumber`, `creationDate`, `order_status_id`, `order_id`) VALUES (?, ?, ?, ?)";
    private static final String GET_ORDER_LOG_BY_ID = "SELECT ol.`id` orderLog_id, ol.`orderNumber`, ol.`creationDate`, os.`status` FROM order_log ol JOIN order_status os ON ol.`order_status_id` = os.`id` WHERE ol.id=?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateOrder(Order orderNumber) {
        jdbcTemplate.update(UPDATE_ORDER_BY_ORDERNUMBER, orderNumber.getOrderNumber(), orderNumber.getStatus(), orderNumber.getOrderDate(), orderNumber.getOrderNumber());
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        return jdbcTemplate.queryForObject(GET_MEMBER_BY_ID, BeanPropertyRowMapper.newInstance(Boolean.class), id);
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return jdbcTemplate.queryForObject(FIND_ORDER_BY_ORDER_NUMBER, BeanPropertyRowMapper.newInstance(Order.class), orderNumber);
    }

    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        List<OrderLog> orderLogsList = jdbcTemplate.query(GET_ORDER_LOGS_BY_ORDER_NO_IN_OLOGS, (rs, rowNum) -> new OrderLog(rs.getString("orderNumber")), orderLogList);
        return orderLogsList;
    }

    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
        return jdbcTemplate.queryForObject(FIND_ORDER_LOG_BY_FIELDS, BeanPropertyRowMapper.newInstance(Boolean.class), orderLog);
    }

    @Override
    public void addOrderLog(boolean add) {
        jdbcTemplate.queryForObject(ADD_ORDER_LOG, BeanPropertyRowMapper.newInstance(OrderLog.class), add);
    }

    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        return jdbcTemplate.queryForObject(GET_ORDER_LOG_BY_ID, BeanPropertyRowMapper.newInstance(OrderLog.class), orderLogId);
    }
}
