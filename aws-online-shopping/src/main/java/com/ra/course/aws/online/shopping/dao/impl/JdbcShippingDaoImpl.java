package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcShippingDaoImpl implements ShippingDao {
    public static final String FIND_SHIPMENT_LOG_BY_ID = "SELECT sl.id, sl.shipmentNumber, ss.status, sl.creationDate FROM shipment_log sl JOIN shipment_status ss ON sl.shipment_status_id = ss.id WHERE sl.id=? ";
    public static final String GET_SHIPMENT_ID ="SELECT id FROM shipment  WHERE shipmentNumber=?";
    public static final String GET_ID_OF_SHIPMENT_STATUS ="SELECT ss.id FROM shipment_status ss WHERE ss.status=?";
    public static final String INSERT_SHIPMENT_LOG = "INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES (?, ?, ?, ?)";
    public  static final String UPDATE_ADDRESS ="UPDATE address SET streetAddress=?, city=?, state=?, zipCode=?, country=? WHERE id=?";

    public static final String GET_ID_OF_ADDRESS_IN_ACCOUNT_BY_MEMBER_ID = "SELECT \n" +
            "a.address_id id\n" +
            "FROM member m JOIN account a ON m.account_id=a.id\n" +
            "WHERE m.id=?";

    public static final String FIND_MEMBER_BY_ID = " SELECT \n" +
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

    public static final String GET_SHIPMENT_BY_SHIPMENT_NUMBER = "SELECT \n" +
            "s.id, s.shipmentNumber, s.shipmentDate, s.estimatedArrival, s.shipmentMethod\n" +
            "FROM  shipment s \n" +
            "WHERE \n" +
            "s.shipmentNumber =?";

    public static final String GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER = "SELECT \n" +
            "sl.id, sl.shipmentNumber,\n" +
            "ss.status, sl.creationDate\n" +
            "FROM  shipment_log sl\n" +
            "JOIN shipment_status ss ON sl.shipment_status_id = ss.id\n" +
            "WHERE \n" +
            "sl.shipmentNumber =? ";


    private final JdbcTemplate jdbcTemplate;
    private final BooleanShipmentLogRowMapper booleanShipmentLogRowMapper;
    private final GetLastIdRowMapper getIdRowMapper;
    private final MemberBooleanRowMapper memberBooleanRowMapper;
    private final ShipmentLogRowMapper sLogRowMapper;
    private final ShipmentRowMapper shipmentRowMapper;

    @Autowired
    public JdbcShippingDaoImpl(JdbcTemplate jdbcTemplate, BooleanShipmentLogRowMapper booleanShipmentLogRowMapper, GetLastIdRowMapper getIdRowMapper, MemberBooleanRowMapper memberBooleanRowMapper, ShipmentLogRowMapper sLogRowMapper, ShipmentRowMapper shipmentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.booleanShipmentLogRowMapper = booleanShipmentLogRowMapper;
        this.getIdRowMapper = getIdRowMapper;
        this.memberBooleanRowMapper = memberBooleanRowMapper;
        this.sLogRowMapper =sLogRowMapper;
        this.shipmentRowMapper = shipmentRowMapper;
    }

    @Override
    public ShipmentLog findShipmentLogById(Long shipmentLogId) {
        try{
            return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_ID, sLogRowMapper, shipmentLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean isThisShipmentLogExist(ShipmentLog shipmentLog) {
        try {
            Long foundId = shipmentLog.getId();
            return jdbcTemplate.queryForObject(FIND_SHIPMENT_LOG_BY_ID, booleanShipmentLogRowMapper, foundId);
        }catch (NullPointerException ex) {
            return false;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList) {
        ShipmentLog shipmentLog = shipmentLogList.get(0);
        String shipmentNumber = shipmentLog.getShipmentNumber();
        List<ShipmentLog> shipmentLogsList = jdbcTemplate.query(GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, sLogRowMapper, shipmentNumber);
        return shipmentLogsList;
    }

    @Override
    public Shipment findByShipmentNumber(String shipmentNumber) {
        try {
            List<ShipmentLog> list = jdbcTemplate.query(GET_SHIPMENT_LOG_BY_SHIPMENT_NUMBER, sLogRowMapper, shipmentNumber);
            Shipment result = jdbcTemplate.queryForObject(GET_SHIPMENT_BY_SHIPMENT_NUMBER, shipmentRowMapper, shipmentNumber);
            Shipment shipment = result;
            shipment.setShipmentLogs(list);
            return shipment;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public void addShipmentLog(ShipmentLog shipmentLog) {
        Integer getIdOfShipment = jdbcTemplate.queryForObject(GET_SHIPMENT_ID, getIdRowMapper, shipmentLog.getShipmentNumber());
        Integer getNumberOfStatus = jdbcTemplate.queryForObject(GET_ID_OF_SHIPMENT_STATUS, getIdRowMapper, shipmentLog.getStatus().toString());
        jdbcTemplate.update(INSERT_SHIPMENT_LOG, shipmentLog.getShipmentNumber(), getNumberOfStatus, shipmentLog.getCreationDate(), getIdOfShipment);
    }

    @Override
    public boolean isFoundMemberID(Long id) {
        try {
            var result = jdbcTemplate.queryForObject(FIND_MEMBER_BY_ID, memberBooleanRowMapper, id);
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    @Override
    public void updateShippingAddress(Member member, Address address) {
      Integer addressId = jdbcTemplate.queryForObject(GET_ID_OF_ADDRESS_IN_ACCOUNT_BY_MEMBER_ID, getIdRowMapper ,member.getMemberID());
      jdbcTemplate.update(UPDATE_ADDRESS, address.getStreetAddress(),address.getCity(),address.getState(), address.getZipCode(), address.getCountry() ,addressId);
    }
}
