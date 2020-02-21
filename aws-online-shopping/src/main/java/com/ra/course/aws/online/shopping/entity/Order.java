package com.ra.course.aws.online.shopping.entity;

import com.ra.course.aws.online.shopping.entity.interfaces.User;

import java.util.Date;
import java.util.Map;

public class Order {
    private int oderID;
    private Map<Product, Integer> productList;
    private int creationOderData;
    private double totalPrice;
    private DeliveryAddress deliveryAddress;
    private User user;
    private PaymentCard paymentCard;
    private Date paymentData;
    private Date paymentDataTime;
    private Date shippingDataTime;

    private boolean isOrderCompleted =false;

    public Order(int oderID, Map<Product, Integer> productList, int creationOderData, double totalPrice, DeliveryAddress deliveryAddress, User user, PaymentCard paymentCard, Date paymentData, Date paymentDataTime, Date shippingDataTime, boolean isOrderCompleted) {
        this.oderID = oderID;
        this.productList = productList;
        this.creationOderData = creationOderData;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.user = user;
        this.paymentCard = paymentCard;
        this.paymentData = paymentData;
        this.paymentDataTime = paymentDataTime;
        this.shippingDataTime = shippingDataTime;
    }

    public int getOderID() {
        return oderID;
    }

    public void setOderID(int oderID) {
        this.oderID = oderID;
    }

    public Map<Product, Integer> getProductList() {
        return productList;
    }

    public void setProductList(Map<Product, Integer> productList) {
        this.productList = productList;
    }

    public int getCreationOderData() {
        return creationOderData;
    }

    public void setCreationOderData(int creationOderData) {
        this.creationOderData = creationOderData;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public Date getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Date paymentData) {
        this.paymentData = paymentData;
    }

    public Date getPaymentDataTime() {
        return paymentDataTime;
    }

    public void setPaymentDataTime(Date paymentDataTime) {
        this.paymentDataTime = paymentDataTime;
    }

    public Date getShippingDataTime() {
        return shippingDataTime;
    }

    public void setShippingDataTime(Date shippingDataTime) {
        this.shippingDataTime = shippingDataTime;
    }









}
