package service;

import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentStatus;

public interface OrderService {
    Order placeOrder();
    void cancelOrder();
    ShipmentStatus trackShipmentStatus();

}
