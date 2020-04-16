package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.BooleanShipmentLogRowMapper;
import com.ra.course.aws.online.shopping.mapper.GetLastIdRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    private static final String FIND_SHIPMENT_LOG_BY_ID = "SELECT sl.id, sl.shipmentNumber, ss.status, sl.creationDate FROM shipment_log sl JOIN shipment_status ss ON sl.shipment_status_id = ss.id WHERE sl.id=? ";
    private static final String GET_SHIPMENT_ID ="SELECT id FROM shipment  WHERE shipmentNumber=?";
    private static final String GET_ID_OF_SHIPMENT_STATUS ="SELECT ss.id FROM shipment_status ss WHERE ss.status=?";
    private static final String INSERT_SHIPMENT_LOG = "INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES (?, ?, ?, ?)";

    private static final String GET_SHIPMENT_BY_SHIPMENT_NUMBER = "SELECT \n" +
            "s.id, s.shipmentNumber, s.shipmentDate, s.estimatedArrival, s.shipmentMethod\n" +
            "FROM  shipment s \n" +
            "WHERE \n" +
            "s.shipmentNumber =?";

    private static final String GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER = "SELECT \n" +
            "sl.id, sl.shipmentNumber,\n" +
            "ss.status, sl.creationDate\n" +
            "FROM  shipment_log sl\n" +
            "JOIN shipment_status ss ON sl.shipment_status_id = ss.id\n" +
            "WHERE \n" +
            "sl.shipmentNumber =? ";


    private final JdbcTemplate jdbcTemplate;
    private final BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;
    private final GetLastIdRowMapper getIdRowMapper;

    @Autowired
    public JdbcShippingDaoImpl(JdbcTemplate jdbcTemplate, BooleanShipmentLogRowMapper booleanShipmentLogRowMapper, GetLastIdRowMapper getIdRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.booleanShipmentLogRowMapper = booleanShipmentLogRowMapper;
        this.getIdRowMapper = getIdRowMapper;
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

    //work correct
    @Override
    public List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList) {
        ShipmentLog shipmentLog = shipmentLogList.get(0);
        String shipmentNumber = shipmentLog.getShipmentNumber();
        List<ShipmentLog> shipmentLogsList = jdbcTemplate.query(GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, BeanPropertyRowMapper.newInstance(ShipmentLog.class), shipmentNumber);
        return shipmentLogsList;
    }

    //work correct
    @Override
    public Shipment findByShipmentNumber(String shipmentNumber) {
        try {
            List<ShipmentLog> list = jdbcTemplate.query(GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, BeanPropertyRowMapper.newInstance(ShipmentLog.class), shipmentNumber);
            Shipment result = jdbcTemplate.queryForObject(GET_SHIPMENT_BY_SHIPMENT_NUMBER, BeanPropertyRowMapper.newInstance(Shipment.class), shipmentNumber);
            Shipment shipment = result;
            shipment.setShipmentLogs(list);
            return shipment;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //work correct?
    @Override
    public void addShipmentLog(ShipmentLog shipmentLog) {
        Integer getIdOfShipment = jdbcTemplate.queryForObject(GET_SHIPMENT_ID, getIdRowMapper, shipmentLog.getShipmentNumber());
        Integer getNumberOfStatus = jdbcTemplate.queryForObject(GET_ID_OF_SHIPMENT_STATUS, getIdRowMapper, shipmentLog.getStatus().toString());
        jdbcTemplate.update(INSERT_SHIPMENT_LOG, shipmentLog.getShipmentNumber(), getNumberOfStatus, shipmentLog.getCreationDate(), getIdOfShipment);
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

    ///??? удалить
    @Override
    public void updateShipment(Shipment shipment) {
      //  jdbcTemplate.update(UPDATE_SHIPMENT_LOG_BY_SL,  shipment.getShipmentLogs());
    }

    @Override
    public Address findThatShippingAddress(Address shippingAddress) {
        //return jdbcTemplate.queryForObject(GET_SHIPMENT_ADDRESS_IN_ACCOUNT, BeanPropertyRowMapper.newInstance(Address.class), shippingAddress);
        return null;
    }
}
