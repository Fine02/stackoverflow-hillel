package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.BooleanShipmentLogRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Component
@Repository
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
    private static final String INSERT_SHIPMENT = "INSERT INTO SHIPMENT (`shipmentNumber`, `shipmentDate`, `estimatedArrival`, `shipmentMethod`) VALUES (?, ?, ?, ?)";

    private static final String GET_SHIPMENT_LOG_BY_SL_ID = "SELECT \n" +
            "sl.`id` shipmentLog_id, sl.`shipmentNumber`, ss.`status` shipmentLogStatus,\n" +
            "sl.`creationDate` \n" +
            "FROM  (`shipment_log` sl\n" +
            "JOIN `shipment` s ON sl.`shipment_id` = s.`id`)\n" +
            ",`shipment_status` ss\n" +
            "WHERE (\n" +
            "sl.`shipment_status_id` = ss.`id` AND sl.`id`=?\n" +
            ") ";



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


    ////
   // private static final String FIND_ORDER_LOG_BY_FIELDS = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status, ol.order_id FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";

   // private static final String GET_ORDER_LOG_LIST_BY_ID = "SELECT ol.id, ol.orderNumber, ol.creationDate, os.status FROM order_log ol JOIN order_status os ON ol.order_status_id = os.id WHERE ol.id=?";
    ////

   // private static final String FIND_SHIPMENT_LOG_BY_ID = "SELECT sl.id, sl.shipmentNumber, ss.status, sl.creationDate FROM shipment_log sl JOIN shipment_status ss ON sl.shipment_status_id = ss.id WHERE sl.`shipmentNumber`=? &&  ss.`status`=? && sl.`creationDate`=? ";


    private static final String FIND_SHIPMENT_LOG_BY_ID = "SELECT sl.id, sl.shipmentNumber, ss.status, sl.creationDate FROM shipment_log sl JOIN shipment_status ss ON sl.shipment_status_id = ss.id WHERE sl.id=? ";


    private final JdbcTemplate jdbcTemplate;
    private final BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;

    @Autowired
    public JdbcShippingDaoImpl(JdbcTemplate jdbcTemplate, BooleanShipmentLogRowMapper booleanShipmentLogRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.booleanShipmentLogRowMapper = booleanShipmentLogRowMapper;
    }

    //work correct
    @Override
    public ShipmentLog findShipmentLogById(Long shipmentLogId) {
        try{
            return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_ID, BeanPropertyRowMapper.newInstance(ShipmentLog.class), shipmentLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //work correct
    @Override
    public boolean isThisShipmentLogExist(ShipmentLog shipmentLog) {
        try {
            Long foundId = shipmentLog.getId();
            return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_ID, booleanShipmentLogRowMapper, foundId);
        }catch (NullPointerException ex) {
            return false;
        }
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
        jdbcTemplate.update(UPDATE_SHIPMENT_LOG_BY_SL,  shipment.getShipmentLogs());
    }
// start
//    @Override
//    public ShipmentLog findShipmentLogById(Long shipmentLogId) {
//        return jdbcTemplate.queryForObject(GET_SHIPMENT_LOG_BY_SL_ID, BeanPropertyRowMapper.newInstance(ShipmentLog.class), shipmentLogId);
//    }
//
//    @Override
//    public boolean isThisShipmentLogExist(ShipmentLog shipmentLog) {
//        return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_ID, BeanPropertyRowMapper.newInstance(Boolean.class), shipmentLog);
//    }
    //end

    @Override
    public List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList) {
        List<ShipmentLog> shipmentLogs = jdbcTemplate.query(GET_SHIPMENT_LOGS_BY_SHIPMENT_NO_IN_OLOGS, (rs, rowNum) -> new ShipmentLog(rs.getString("orderNumber")), shipmentLogList);
        return shipmentLogs;
    }

    @Override
    public Shipment findByShipmentNumber(String shipmentNumber) {
        return jdbcTemplate.queryForObject(FIND_SHIPMENT_BY_SHIPMENT_NUMBER, BeanPropertyRowMapper.newInstance(Shipment.class), shipmentNumber);
    }

    //
    @Override
    public void addShipmentLog(boolean add) {
        jdbcTemplate.queryForObject(ADD_SHIPMENT_LOG, BeanPropertyRowMapper.newInstance(OrderLog.class), add);
    }

    @Override
    public Address findThatShippingAddress(Address shippingAddress) {
        return jdbcTemplate.queryForObject(GET_SHIPMENT_ADDRESS_IN_ACCOUNT, BeanPropertyRowMapper.newInstance(Address.class), shippingAddress);
    }
}
