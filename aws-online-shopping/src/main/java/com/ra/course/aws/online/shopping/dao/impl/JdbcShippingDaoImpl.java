package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Account;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcShippingDaoImpl implements ShippingDao {

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


    private static final String UPDATE_SHIPMENT_ADDRESS_IN_MEMBER_ACCOUNT = "";

    private static final String UPDATE_SHIPMENT_LOG_BY_SL = "UPDATE `SHIPMENT_LOG` sl SET `shipmentNumber`=?, `shipment_status_id`=?, `creationDate`=?, `shipment_id` =? WHERE sl.`shipmentNumber`= ? AND sl.`shipment_status_id`=? AND sl.`creationDate`=? AND sl.`shipment_id`=?";
    private static final String UPDATE_SHIPMENT_LOG_BY_SHIPMENT_NO = "UPDATE `SHIPMENT_LOG` sl SET `shipmentNumber`=?, `shipment_status_id`=?, `creationDate`=?, `shipment_id` =? WHERE sl.`shipmentNumber`= ? AND sl.`shipment_status_id`=? AND sl.`creationDate`=? AND sl.`shipment_id`=?";

    private static final String GET_SHIPMENT_LOG_BY_SL_ID = "SELECT \n" +
            "sl.`id` shipmentLog_id, sl.`shipmentNumber`, ss.`status` shipmentLogStatus,\n" +
            "sl.`creationDate` \n" +
            "FROM  (`shipment_log` sl\n" +
            "JOIN `shipment` s ON sl.`shipment_id` = s.`id`)\n" +
            ",`shipment_status` ss\n" +
            "WHERE (\n" +
            "sl.`shipment_status_id` = ss.`id` AND sl.`id`=?\n" +
            ") ";

    private static final String FIND_SHIPMENT_LOG_BY_FIELDS = "SELECT sl.`id`, sl.`shipmentNumber`, ss.`status`, sl.`creationDate` FROM shipment_log sl JOIN shipment_status ss ON sl.`shipment_status_id` = ss.`id` WHERE sl.`shipmentNumber`=? &&  ss.`status`=? && sl.`creationDate`=? ";

    private static final String GET_SHIPMENT_LOGS_BY_SHIPMENT_NO_IN_OLOGS ="SELECT \n" +
            "sl.`id` shipmentLog_id, sl.`shipmentNumber`, ss.`status` shipmentLogStatus,\n" +
            "sl.`creationDate` \n" +
            "FROM  (`shipment_log` sl\n" +
            "JOIN `shipment` s ON sl.`shipment_id` = s.`id`)\n" +
            ",`shipment_status` ss\n" +
            "WHERE (\n" +
            "sl.`shipment_status_id` = ss.`id` AND s.`shipmentNumber`=? \n" +
            ")";

    private static final String FIND_SHIPMENT_BY_SHIPMENT_NUMBER = "SELECT \n" +
            "s.`id` shipment_id, s.`shipmentNumber`, s.`shipmentDate`, s.`estimatedArrival`, s.`shipmentMethod`,\n" +
            "sl.`id` shipmentLog_id, sl.`shipmentNumber`, ss.`status` shipmentLogStatus,\n" +
            "sl.`creationDate` \n" +
            "FROM  (`shipment_log` sl\n" +
            "JOIN `shipment` s ON sl.`shipment_id` = s.`id`)\n" +
            ",`shipment_status` ss\n" +
            "WHERE (\n" +
            " sl.`shipment_status_id` = ss.`id` AND s.`shipmentNumber`=? \n" +
            ")";

    private static final String ADD_SHIPMENT_LOG = "INSERT INTO `shipment_log` (`shipmentNumber`, `shipment_status_id`, `creationDate`, `shipment_id`) VALUES (?, ?, ?, ?)";

    private static final String GET_SHIPMENT_ADDRESS_IN_ACCOUNT = "SELECT \n" +
            "adr.`streetAddress`, adr.`city`, adr.`state`, adr.`zipcode`, adr.`country`\n" +
            "FROM member m JOIN account a ON m.`account_id`= a.`id`\n" +
            "JOIN `address` adr ON a.`address_id` = adr.`id`\n" +
            "WHERE adr.`streetAddress`=? && adr.`city`=? &&  adr.`state` =? && adr.`zipcode`=? && adr.`country` =?  GROUP BY a.id";

    private final JdbcTemplate jdbcTemplate;

    public JdbcShippingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public boolean findShippingAddress(Address address) {
        return false;
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        return false;
    }

    @Override
    public void updateShippingAddress(Member address) {
    }

    @Override
    public void updateShipment(Shipment shipment) {
        jdbcTemplate.update(UPDATE_SHIPMENT_LOG_BY_SL, shipment.getOrderNumber(), orderNumber.getStatus(), orderNumber.getOrderDate(), orderNumber.getOrderNumber());
    }

    @Override
    public ShipmentLog findShipmentLogById(Long shipmentLogId) {
        return jdbcTemplate.queryForObject(GET_SHIPMENT_LOG_BY_SL_ID, BeanPropertyRowMapper.newInstance(ShipmentLog.class), shipmentLogId);
    }

    @Override
    public boolean isThisShipmentLogExist(ShipmentLog shipmentLog) {
        return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_FIELDS, BeanPropertyRowMapper.newInstance(Boolean.class), shipmentLog);
    }

    @Override
    public List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList) {
        List<ShipmentLog> shipmentLogs = jdbcTemplate.query(GET_SHIPMENT_LOGS_BY_SHIPMENT_NO_IN_OLOGS, (rs, rowNum) -> new ShipmentLog(rs.getString("orderNumber")), shipmentLogList);
        return shipmentLogs;
    }

    @Override
    public Shipment findByShipmentNumber(String shipmentNumber) {
        return jdbcTemplate.queryForObject(FIND_SHIPMENT_BY_SHIPMENT_NUMBER, BeanPropertyRowMapper.newInstance(Shipment.class), shipmentNumber);
    }

    @Override
    public void addShipmentLog(boolean add) {
        jdbcTemplate.queryForObject(ADD_SHIPMENT_LOG, BeanPropertyRowMapper.newInstance(OrderLog.class), add);
    }

    @Override
    public Address findThatShippingAddress(Address shippingAddress) {
        return jdbcTemplate.queryForObject(GET_SHIPMENT_ADDRESS_IN_ACCOUNT, BeanPropertyRowMapper.newInstance(Address.class), shippingAddress);
    }
}
