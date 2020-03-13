package com.ra.course.aws.online.shopping.dao;

import com.ra.course.aws.online.shopping.entity.notification.EmailNotification;
import com.ra.course.aws.online.shopping.entity.notification.SMSNotification;
import com.ra.course.aws.online.shopping.entity.order.Order;
import com.ra.course.aws.online.shopping.entity.order.OrderLog;
import com.ra.course.aws.online.shopping.entity.shipment.Shipment;
import com.ra.course.aws.online.shopping.entity.shipment.ShipmentLog;

import java.util.List;

public interface NotificationDao {
    Shipment findByShipmentNumber(String shipmentNumber);

    Order findByOrderNumber(String orderNumber);

    List<OrderLog> findLogListByOrder(List<OrderLog> orderLogList);

    SMSNotification createSMSNotification(SMSNotification smsNotification);

    EmailNotification createEmailNotification(EmailNotification emailNotification);

    List<ShipmentLog> findLogListByShipment(List<ShipmentLog> shipmentLogList);

    boolean isThisOrderExist(OrderLog orderLog);

    boolean isThisOrderLogExist(OrderLog orderLog);

    boolean isThisShipmentLogExist(ShipmentLog shipmentLog);

    void addOrderLog(boolean add);

    void addShipmentLog(boolean add);

    boolean isFoundMemberPhoneNumber(String string);

    boolean isFoundMemberEmail(String string);

    OrderLog findOrderLogById(Long orderLogId);

    void updateOrder(Order order);
    void updateShipment(Shipment shipment);

    ShipmentLog findShipmentLogById (Long shipmentLogId);
}
