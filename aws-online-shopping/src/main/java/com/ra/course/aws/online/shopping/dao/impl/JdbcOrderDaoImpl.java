package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.mapper.OrderLogRowMapper;
import com.ra.course.aws.online.shopping.mapper.ListOrderLogRowMapper;
import com.ra.course.aws.online.shopping.mapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Repository
@Component
public class JdbcOrderDaoImpl implements OrderDao {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRowMapper orderRowMapper;
    private final ListOrderLogRowMapper orderLogRowMapper;
    private final OrderLogRowMapper orderLogOneRowMapper;

    @Autowired
    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate, OrderRowMapper orderRowMapper, ListOrderLogRowMapper orderLogRowMapper, OrderLogRowMapper orderLogOneRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRowMapper = orderRowMapper;
        this.orderLogRowMapper = orderLogRowMapper;
        this.orderLogOneRowMapper = orderLogOneRowMapper;
    }

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
            "ol.id orderLog_id, ol.orderNumber,\n" +
            "ol.creationDate, os.status orderLogStatus\n" +
            "FROM  (order_log ol\n" +
            "JOIN \"order\" o ON ol.order_id = o.id)\n" +
            ",order_status os, order_status os2\n" +
            "WHERE (\n" +
            "ol.order_status_id = os.id AND o.order_status_id = os2.id AND ol.orderNumber=? \n" +
            ") ";

    private static final String FIND_ORDER_LOG_BY_FIELDS = "SELECT ol.`id`, ol.`orderNumber`, ol.`creationDate`, os.`status`, ol.`order_id` FROM order_log ol JOIN order_status os ON ol.`order_status_id` = os.`id` WHERE ol.`orderNumber`=? && ol.`creationDate`=? && os.`status`=?";
    private static final String ADD_ORDER_LOG = "INSERT INTO `order_log` (`orderNumber`, `creationDate`, `order_status_id`, `order_id`) VALUES (?, ?, ?, ?)";
    private static final String GET_ORDER_LOG_BY_ID = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";



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
//        return jdbcTemplate.queryForObject(FIND_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber);
//        List<Order> orderLogsList = jdbcTemplate.query(FIND_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber);
        List<Order> list = jdbcTemplate.query(FIND_ORDER_BY_ORDER_NUMBER, orderRowMapper, orderNumber);
        Order order = new Order();
        List<OrderLog> orderLogList = null;
        ArrayList<OrderLog> arL = new ArrayList<>();
        for(Order row: list){
            OrderLog ol = new OrderLog();
            order.setOrderNumber(row.getOrderNumber());
            order.setStatus(row.getStatus());
            order.setOrderDate(row.getOrderDate());
            ol.setId(row.getOrderLog().get(0).getId());
            ol.setOrderNumber(row.getOrderLog().get(0).getOrderNumber());
            ol.setCreationDate(row.getOrderLog().get(0).getCreationDate());
            ol.setStatus(row.getOrderLog().get(0).getStatus());
//            orderLogList.add(ol);
            arL.add(ol);
        }
//        order.setOrderLog(orderLogList);
        order.setOrderLog(arL);
        return order;
    }

    //work well
    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        OrderLog orderLog = orderLogList.get(0);
        String orderNumber =orderLog.getOrderNumber();
        List<OrderLog> orderLogsList = jdbcTemplate.query(GET_ORDER_LOGS_BY_ORDER_NO_IN_OLOGS, orderLogOneRowMapper, orderNumber);
        return orderLogsList;
    }


    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
     //   return jdbcTemplate.queryForObject(FIND_ORDER_LOG_BY_FIELDS, BeanPropertyRowMapper.newInstance(Boolean.class), orderLog);
        return true;
    }

    @Override
    public void addOrderLog(boolean add) {
    //    jdbcTemplate.queryForObject(ADD_ORDER_LOG, BeanPropertyRowMapper.newInstance(OrderLog.class), add);
    }

    //work well
    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        return jdbcTemplate.queryForObject(GET_ORDER_LOG_BY_ID, BeanPropertyRowMapper.newInstance(OrderLog.class), orderLogId);
    }
}
