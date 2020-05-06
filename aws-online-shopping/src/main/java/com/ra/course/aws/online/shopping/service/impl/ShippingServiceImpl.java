package com.ra.course.aws.online.shopping.service.impl;

import com.ra.course.aws.online.shopping.dao.ShippingDao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import com.ra.course.aws.online.shopping.exceptions.MemberDataNotFoundException;
import com.ra.course.aws.online.shopping.exceptions.ShipmentLogIsAlreadyExistException;
import com.ra.course.aws.online.shopping.service.ShippingService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {
    private transient final ShippingDao shippingDao;

    public ShippingServiceImpl(final ShippingDao addressDao) {
        this.shippingDao = addressDao;
    }

    @Override
    public boolean specifyShippingAddress(final Member member, final Address address) {
        if (shippingDao.isFoundMemberID(member.getMemberID())) {
            member.getAccount().setShippingAddress(address);
            shippingDao.updateShippingAddress(member, address);
            return true;
        }
        throw new MemberDataNotFoundException("There is not found the Member by this ID");
    }

    @Override
    public List<ShipmentLog> getShipmentTrack(final String shipmentNumber) {

        final var foundShipment = shippingDao.findByShipmentNumber(shipmentNumber);
        if (foundShipment != null) {
            return shippingDao.findLogListByShipment(foundShipment.getShipmentLogs());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean addShipmentLogToShipment(final Shipment shipment, final ShipmentLog shipmentLog) {
        final var foundShipment = shippingDao.findByShipmentNumber(shipment.getShipmentNumber());
        if (foundShipment.getShipmentLogs().contains(shipmentLog)) {
            throw new ShipmentLogIsAlreadyExistException("This ShipmentLog is already exist");
        }
        shippingDao.addShipmentLog(shipmentLog);
        return true;
    }
}


