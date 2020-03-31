package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.OrderDao;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcOrderDaoImpl implements OrderDao {
    private static final String SELECT_UPDATE_SQL = "UPDATE `ORDER` SET ORDERNUMBER, ";
    private static final String SELECT_ORDER_LOG_SQL = "";

    private static final String GET_ORDER_LOG_BY_ID = "";

    private static final String GET_MEMBER_BY_ID = "SELECT \n" +
            "\tm.`account_id` id,\n" +
            "        a.`userName`, a.`password`,\n" +
            "        acs.`status`,\n" +
            "        a.`name`,\n" +
            "        adr.`streetAddress`, adr.`city`, adr.`state`, adr.`zipcode`, adr.`country`,\n" +
            "        a.`email`,\n" +
            "        a.`phone`,\n" +
            "        crc.`nameOnCard`, crc.`cardNumber`,crc.`code`, adr.`streetAddress`, adr.`city`, adr.`state`, adr.`zipcode`, adr.`country`,\n" +
            "        ebt.`bankName`, ebt.`routingNumber`, ebt.`accountNumber`\n" +
            "FROM member m JOIN account a ON m.`account_id`= a.`id`\n" +
            "JOIN account_status acs ON a.`account_status_id` = acs.`id`\n" +
            "JOIN `address` adr ON a.`address_id` = adr.`id`\n" +
            "JOIN `credit_card` crc ON a.`creditCardList_id` = crc.`id`\n" +
            "JOIN `electronic_bank_transfer` ebt ON a.`electronicBankTransferList_id` = ebt.`id`\n" +
            "WHERE m.id=? GROUP BY a.id";


    private static final String SELECT_SMS_Notification_SQL = "";

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateOrder(Order orderNumber) {
          //jdbcTemplate.queryForObject()
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        return false;
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList) {
        return null;
    }

    @Override
    public boolean isThisOrderLogExist(OrderLog orderLog) {
        return false;
    }

    @Override
    public void addOrderLog(boolean add) {

    }

    @Override
    public OrderLog findOrderLogById(Long orderLogId) {
        return null;
    }
}
