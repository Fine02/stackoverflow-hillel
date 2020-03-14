package com.ra.course.aws.online.shopping.service;

import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;

import java.util.List;

public interface ShippingService {
    Address specifyShippingAddress (Member member, Address address) ;

    List<ShipmentLog> getShipmentTrack (String shipmentNumber);
}
