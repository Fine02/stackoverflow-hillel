package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcShippingDaoImpl implements ShippingDao {
    public static final String FIND_SLOG = "SELECT sl.id, sl.shipmentNumber, ss.status, sl.creationDate FROM shipment_log sl JOIN shipment_status ss ON sl.shipment_status_id = ss.id WHERE sl.id=? ";
    public static final String GET_SHIPMENT_ID ="SELECT id FROM shipment  WHERE shipmentNumber=?";
    public static final String GET_STATUS_ID ="SELECT ss.id FROM shipment_status ss WHERE ss.status=?";
    public static final String INSERT_SLOG = "INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES (?, ?, ?, ?)";
    public  static final String UPDATE_ADDRESS ="UPDATE address SET streetAddress=?, city=?, state=?, zipCode=?, country=? WHERE id=?";

    public static final String GET_ADDRESS_ID = "SELECT a.address_id id\n" +
            "FROM member m JOIN account a ON m.account_id=a.id WHERE m.id=?";

    public static final String FIND_MEMBER_BY_ID = "SELECT m.account_id,\n" +
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

    public static final String GET_SHIPMENT = "SELECT s.id, s.shipmentNumber, s.shipmentDate, s.estimatedArrival, s.shipmentMethod\n" +
            "FROM  shipment s \n" +
            "WHERE \n" +
            "s.shipmentNumber =?";

    public static final String GET_SHIPMENT_LOG = "SELECT \n" +
            "sl.id, sl.shipmentNumber,\n" +
            "ss.status, sl.creationDate\n" +
            "FROM  shipment_log sl\n" +
            "JOIN shipment_status ss ON sl.shipment_status_id = ss.id\n" +
            "WHERE sl.shipmentNumber =?";


    private transient final JdbcTemplate jdbcTemplate;
    private transient final GetLastIdRowMapper getId;
    private transient final ShipmentLogRowMapper sLogRowMapper;

    @Autowired

    public JdbcShippingDaoImpl(final JdbcTemplate jdbcTemplate, final GetLastIdRowMapper getId, final ShipmentLogRowMapper sLogRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.getId = getId;
        this.sLogRowMapper =sLogRowMapper;
    }

    @Override
    public ShipmentLog findShipmentLogById(final Long shipmentLogId) {
        try{
            return jdbcTemplate.queryForObject(FIND_SLOG, sLogRowMapper, shipmentLogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean isThisShipmentLogExist(final ShipmentLog shipmentLog) {
        try {
            final Long foundId = shipmentLog.getId();
            return jdbcTemplate.queryForObject(FIND_SLOG, new BooleanShipmentLogRowMapper(), foundId);
        }catch (NullPointerException ex) {
            return false;
        } catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public List<ShipmentLog> findLogListByShipment(final List<ShipmentLog> shipmentLogList) {
        final ShipmentLog shipmentLog = shipmentLogList.get(0);
        final String shipmentNumber = shipmentLog.getShipmentNumber();
        return jdbcTemplate.query(GET_SHIPMENT_LOG, sLogRowMapper, shipmentNumber);
    }

    @Override
    public Shipment findByShipmentNumber(final String shipmentNumber) {
        try {
            final List<ShipmentLog> list = jdbcTemplate.query(GET_SHIPMENT_LOG, sLogRowMapper, shipmentNumber);
            final Shipment result = jdbcTemplate.queryForObject(GET_SHIPMENT, new ShipmentRowMapper(), shipmentNumber);
            final Shipment shipment = result;
            shipment.setShipmentLogs(list);
            return shipment;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public void addShipmentLog(final ShipmentLog shipmentLog) {
        final Integer getIdOfShipment = jdbcTemplate.queryForObject(GET_SHIPMENT_ID, getId, shipmentLog.getShipmentNumber());
        final Integer getNumberOfStatus = jdbcTemplate.queryForObject(GET_STATUS_ID, getId, shipmentLog.getStatus().toString());
        jdbcTemplate.update(INSERT_SLOG, shipmentLog.getShipmentNumber(), getNumberOfStatus, shipmentLog.getCreationDate(), getIdOfShipment);
    }

    @Override
    public boolean isFoundMemberID(final Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_MEMBER_BY_ID, new MemberBooleanRowMapper (), id);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

//    @Override
//    public boolean isFoundMemberID(final Long id) {
//        try {
//            final var result = jdbcTemplate.queryForObject(FIND_MEMBER_BY_ID, new MemberBooleanRowMapper (), id);
//            return result==null?false:true;
//        } catch (EmptyResultDataAccessException ex) {
//            return false;
//        }
//    }

    @Override
    public void updateShippingAddress(final Member member, final Address address) {
        final Integer addressId = jdbcTemplate.queryForObject(GET_ADDRESS_ID, getId,member.getMemberID());
      jdbcTemplate.update(UPDATE_ADDRESS, address.getStreetAddress(),address.getCity(),address.getState(), address.getZipCode(), address.getCountry() ,addressId);
    }
}
