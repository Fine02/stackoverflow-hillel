package com.ra.course.aws.online.shopping.dao.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcShippingDaoImpl implements ShippingDao {
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

    }

    @Override
    public ShipmentLog findShipmentLogById(Long shipmentLogId) {
        return null;
    }

    @Override
    public boolean isThisShipmentLogExist(ShipmentLog shipmentLog) {
        return false;
    }

    @Override
    public List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList) {
        return null;
    }

    @Override
    public Shipment findByShipmentNumber(String shipmentNumber) {
        return null;
    }

    @Override
    public void addShipmentLog(boolean add) {

    }

    @Override
    public Address findThatShippingAddress(Address shippingAddress) {
        return null;
    }
}
