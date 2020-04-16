package com.ra.course.aws.online.shopping.dao;
import com.ra.course.aws.online.shopping.entity.Address;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;
import com.ra.course.aws.online.shopping.entity.user.Member;
import java.util.List;

public interface ShippingDao {
    boolean findShippingAddress(Address address);

    boolean isFoundMemberID(Long id);

    void updateShippingAddress(Member address);

    void updateShipment(Shipment shipment);

    ShipmentLog findShipmentLogById (Long shipmentLogId);

    boolean isThisShipmentLogExist(ShipmentLog shipmentLog);

    List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList);

    Shipment findByShipmentNumber(String shipmentNumber);

    //delete?
    //void addShipmentLog(boolean add);

    void addShipmentLog(ShipmentLog shipmentLog);

    Address findThatShippingAddress(Address shippingAddress);

}
