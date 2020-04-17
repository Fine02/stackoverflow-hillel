package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

import java.util.List;

public interface ShippingService {
    boolean specifyShippingAddress (Member member, Address address) ;

    List<ShipmentLog> getShipmentTrack (String shipmentNumber);

    boolean addShipmentLogToShipment(Shipment shipment, ShipmentLog shipmentLog);
}
